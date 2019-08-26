package sun.flower.dubbo.common.filter;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;

/**
 * support helper operations
 *
 * @author jing.guo
 * @date 2018/10/31
 */
public abstract class BaseFilter {

    /**
     * 从RpcConext中获取指定Attachment
     *
     * @param key attachment key
     */
    protected String getAttachment(String key) {
        return RpcContext.getContext().getAttachment(key);
    }

    /**
     * 向RpcConext中设置Attachment
     *
     * @param key attachment key
     */
    protected void setAttachment(String key, String value) {
        RpcContext.getContext().getAttachments().put(key, value);
    }

    /**
     * 获取Url中配置参数
     *
     * @param pName parameter name
     */
    protected String getUrlParameter(Invoker<?> invoker, String pName) {
        return invoker.getUrl().getParameter(pName);
    }
}
