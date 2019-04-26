package com.sun.flower.redisson.rate;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/26 11:33
 **/
@Slf4j
@Component
public class RateLimiterDemo {

    @Autowired
    RedissonClient redissonClient;

    public boolean isRateLimiter(String key) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        boolean bool = rateLimiter.trySetRate(RateType.OVERALL, 20, 1, RateIntervalUnit.SECONDS);
        log.info("bool = {}", bool);

        // rateLimiter.tryAcquire(1, 100, TimeUnit.MILLISECONDS);
        boolean acq = rateLimiter.tryAcquire();

        if (!acq) {
            return false;
        }

        return true;
    }

}
