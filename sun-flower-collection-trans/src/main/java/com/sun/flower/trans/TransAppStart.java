package com.sun.flower.trans;

import com.sun.flower.filter.LoggerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/3/22 20:55
 **/
@SpringBootApplication
public class TransAppStart {

    public static void main(String[] args) {
        SpringApplication.run(TransAppStart.class, args);
    }

    /**
     * 初始化质量日志
     * @return
     */
    @Bean
    @Order(0)
    public FilterRegistrationBean initLoggerFilter() {
        LoggerFilter loggerFilter = new LoggerFilter("sun-flower-collection-trans");

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(loggerFilter);

        return filterRegistrationBean;
    }

}
