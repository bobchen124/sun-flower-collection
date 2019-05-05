package com.sun.flower.mq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/2 17:51
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MqBaseTest {

    @Resource
    AmqpTemplate amqpTemplate;

    @Test
    public void sendTest() {
        //Message message = amqpTemplate.("test-demo", "test-msg");

        //log.info("ret = {}", JSONObject.toJSONString(ret));
    }

}
