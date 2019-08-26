package sun.flower.order.service;

import org.apache.dubbo.config.annotation.Service;
import sun.flower.api.order.IOrderService;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/8/26 14:32
 **/
@Service(version = "1.0", interfaceClass = IOrderService.class)
public class OrderServiceImpl implements IOrderService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }

}
