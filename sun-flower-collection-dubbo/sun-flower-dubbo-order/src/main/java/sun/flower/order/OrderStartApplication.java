package sun.flower.order;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/8/26 14:20
 **/
@SpringBootApplication
@DubboComponentScan("sun.flower.order")
public class OrderStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderStartApplication.class, args);
    }

}
