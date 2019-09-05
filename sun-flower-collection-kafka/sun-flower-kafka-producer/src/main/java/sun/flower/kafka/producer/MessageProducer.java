package sun.flower.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/9/5 14:16
 **/
@Component
@Slf4j
public class MessageProducer implements InitializingBean {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        ListenableFuture<SendResult> resultListenableFuture = kafkaTemplate.send("kafka-test-topic", "kafka-test-topic-message");
        log.info("====kakka send msg = [{}]", resultListenableFuture.get().toString());
    }

}
