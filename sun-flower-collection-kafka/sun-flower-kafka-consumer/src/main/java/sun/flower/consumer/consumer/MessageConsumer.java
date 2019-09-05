package sun.flower.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/9/5 15:45
 **/
@Component
@Slf4j
public class MessageConsumer implements InitializingBean {

    @KafkaListener(topics = "kafka-test-topic")
    public void processMessage(String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }

        log.info("=========== message = [{}]", message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
