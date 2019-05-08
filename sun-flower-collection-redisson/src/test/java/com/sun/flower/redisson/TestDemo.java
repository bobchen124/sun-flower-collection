package com.sun.flower.redisson;

import com.sun.flower.redisson.entity.DemoOne;
import com.sun.flower.redisson.rate.RateLimiterDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/26 11:39
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

    @Autowired
    RateLimiterDemo rateLimiterDemo;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testLimit() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            System.out.println(System.currentTimeMillis() + "===" + i + rateLimiterDemo.isRateLimiter("redisson-key-limiter"));
        }

        Thread.sleep(1000L);

        for (int i = 0; i < 10; i++) {
            System.out.println(System.currentTimeMillis() + "===" + i + rateLimiterDemo.isRateLimiter("redisson-key-limiter"));
        }

        System.out.println("====" + ((System.currentTimeMillis()) - start));
    }

    @Test
    public void testBucket() {
        RBucket<String> bucket = redissonClient.getBucket("bucket-test-0n33-001");
        log.info("get bucket , {}", (bucket == null));

        bucket.set("test", 2, TimeUnit.SECONDS);

        for (;;) {
            log.info("get-one, isExists = {}, val = {}", bucket.isExists(), bucket.get());

            if (bucket.get() == null) {
                log.info("cache val is null, isExists = {}, val = {}", bucket.isExists(), bucket.get());
                break;
            }
        }
    }

    public void testAtomicLong() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong("atomic-long-test");
        long v = atomicLong.incrementAndGet();
        log.info("val = {}", v);

        if (v == 1L) {
            atomicLong.expire(1, TimeUnit.MINUTES);
        }

        if (v > 10) {
            log.info("more than limit");
        }
    }

    @Test
    public void testDemo() {
        DemoOne demoOne = getDemoOne("test-one", 2019);
        log.info("testDemo ret, {}", demoOne.toString());
    }

    public DemoOne getDemoOne(String name, Integer age) {
        DemoOne demoOne;
        RBucket<DemoOne> bucket = redissonClient.getBucket("bucket-test-0n33-test-01-01");

        if (bucket != null && (demoOne = bucket.get()) != null) {
            log.info("bucket ret, {}", demoOne.toString());
            return demoOne;
        }

        log.info("DemoOne ret = {}", bucket.get());
        demoOne = new DemoOne();
        demoOne.setAge(age);
        demoOne.setName(name);

        bucket.set(demoOne, 10, TimeUnit.MINUTES);
        return demoOne;
    }

}
