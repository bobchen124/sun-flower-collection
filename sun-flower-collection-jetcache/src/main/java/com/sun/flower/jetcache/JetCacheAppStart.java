package com.sun.flower.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 09:25
 **/
@EnableCreateCacheAnnotation
@ComponentScan(basePackages="com.sun.flower")
@SpringBootApplication
public class JetCacheAppStart {

    public static void main(String[] args) {
        SpringApplication.run(JetCacheAppStart.class, args);
    }

}
