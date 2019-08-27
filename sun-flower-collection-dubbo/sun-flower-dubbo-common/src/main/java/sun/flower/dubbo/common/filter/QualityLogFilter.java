package sun.flower.dubbo.common.filter;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc: 质量日志拦截，记录服务质量问题
 * @Author: chenbo
 * @Date: 2019/8/27 10:59
 **/
public class QualityLogFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(QualityLogFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long begin = System.currentTimeMillis();

        Result result = invoker.invoke(invocation);

        long end = System.currentTimeMillis();
        logger.info("==, {}, {}, {}, {}, {}", invocation.getMethodName(), invocation.getArguments(),
                invocation.getInvoker().getInterface(), result.getValue(), (end - begin));

        return result;
    }

}
