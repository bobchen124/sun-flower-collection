package com.sun.flower.redisson;

import com.sun.flower.redisson.rate.RateLimiterDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void testBucket() throws Exception {
        RBucket<String> bucket = redissonClient.getBucket("bucket-test-0n33");
        log.info("get bucket , {}", (bucket == null));

        bucket.set("test", 1, TimeUnit.SECONDS);
        log.info("{}", bucket.isExists());
        log.info("get-one , {}", bucket.get());

        Thread.sleep(1000);
        log.info("{}", bucket.isExists());
        log.info("get-two, {}", bucket.get());
    }

}
