package com.sun.flower.trans.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc: 测试controller
 * @Author: chenbo
 * @Date: 2019/4/2 15:37
 **/
@RestController
@RequestMapping("/test")
@Slf4j
public class DemoController {

    /**
     * 测试方法
     * @param paramOne
     * @param paramTwo
     * @return
     */
    @GetMapping("/demo")
    public String demoOne(String paramOne, Integer paramTwo) {
        log.info("paramOne = {}, paramTwo = {}", paramOne, paramTwo);
        return "demo ok";
    }

    /**
     * 测试方法二
     * @param paramOne
     * @param paramTwo
     * @return
     */
    @PostMapping("/demo")
    public String demoTWo(String paramOne, Integer paramTwo) {
        log.info("paramOne = {}, paramTwo = {}", paramOne, paramTwo);
        return "demo two ok";
    }

}
