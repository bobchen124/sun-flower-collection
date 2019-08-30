package sun.flower.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;

import java.util.concurrent.Future;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/8/30 09:51
 **/
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        //return "Hystrix Hello " + name + "!";
        throw new RuntimeException("this command always fails");
    }

    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }

    public static void main(String[] args) throws Exception {
        String str = new CommandHelloWorld("Bob").execute();
        System.out.println("--------, str = " + str);

        // 异步方式
        Future<String> sf = new CommandHelloWorld("Bob-Future").queue();
        System.out.println("--------, future sf = " + sf.get());

        // 异步，订阅方式
        Observable<String> obs = new CommandHelloWorld("Bob-Observable").observe();
        System.out.println("------, Observable obs = " + obs.asObservable().toBlocking().toFuture().get());
    }

}
