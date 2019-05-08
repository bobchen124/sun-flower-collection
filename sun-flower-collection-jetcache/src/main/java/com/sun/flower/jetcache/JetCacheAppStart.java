package com.sun.flower.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 09:25
 **/
@EnableCreateCacheAnnotation
@SpringBootApplication
public class JetCacheAppStart {

    public static void main(String[] args) {
        SpringApplication.run(JetCacheAppStart.class, args);
    }

}
