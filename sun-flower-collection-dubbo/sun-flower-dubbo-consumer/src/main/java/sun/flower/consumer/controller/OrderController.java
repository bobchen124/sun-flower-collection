package sun.flower.consumer.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.flower.api.order.IOrderService;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/8/26 15:08
 **/
@RestController
@RequestMapping("/order")
public class OrderController implements InitializingBean {

    @Reference(version = "1.0", check = false)
    IOrderService orderService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("===" + orderService.sayHello("start..."));
    }
}
