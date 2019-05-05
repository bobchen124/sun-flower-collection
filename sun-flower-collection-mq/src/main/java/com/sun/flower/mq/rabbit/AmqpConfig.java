package com.sun.flower.mq.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/2 17:55
 **/
@Component
@Slf4j
public class AmqpConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initAmqpTemplate() {
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //rabbitTemplate.setEncoding("UTF-8");
        //rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((CorrelationData var1, boolean ack, String cause) -> {
            log.info("");
            if (ack) {
                log.info("消息发送成功，var1 = {}", var1);
            } else {
                log.info("消息发送失败，cause = {}", cause);
            }
        });

        //return rabbitTemplate;
    }

    @Bean
    public Queue demoQueue() {
        return new Queue("test-demo");
    }

}
