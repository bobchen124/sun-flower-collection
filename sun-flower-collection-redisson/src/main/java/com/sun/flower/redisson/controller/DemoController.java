package com.sun.flower.redisson.controller;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/5 18:16
 **/
@RestController
public class DemoController {

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/redission/set")
    public String setVal() {
        RBucket<String> bucket = redissonClient.getBucket("RedissionController-test-set");
        bucket.set("RedissionController-test-set", 10, TimeUnit.MINUTES);

        return "succ";
    }

    @GetMapping("/redission/get")
    public String getVal() {
        RBucket<String> bucket = redissonClient.getBucket("RedissionController-test-set");

        String demoOne = bucket.get();
        if (demoOne != null) {
            return demoOne.toString();
        }

        return "no cache";
    }

    @GetMapping("/redission/no")
    public String getVal2() {
        return "no cache ....";
    }

}
