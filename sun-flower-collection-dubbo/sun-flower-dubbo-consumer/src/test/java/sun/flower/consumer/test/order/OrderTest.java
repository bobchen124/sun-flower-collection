package sun.flower.consumer.test.order;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import sun.flower.api.order.IOrderService;
import sun.flower.consumer.test.BaseTest;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/8/27 11:59
 **/
public class OrderTest extends BaseTest {

    @Reference(version = "1.0", check = false)
    IOrderService orderService;

    @Test
    public void sayHelloTest() {
        System.out.println(orderService.sayHello("test...."));
    }

}
