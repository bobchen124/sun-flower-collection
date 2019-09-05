package sun.flower.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/9/5 15:44
 **/
@SpringBootApplication
public class KafkaAppStart {

    public static void main(String[] args) {
        SpringApplication.run(KafkaAppStart.class, args);
    }

}
