package com.sun.flower.filter;

import com.sun.flower.support.compactor.ContentCompactor;
import com.sun.flower.support.compactor.CutoffContentCompactor;
import com.sun.flower.support.http.HttpProcessObserver;
import com.sun.flower.support.http.HttpResponseWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Desc: 日志过滤器
 * @Author: chenbo
 * @Date: 2019/3/22 19:22
 **/
@Slf4j
public class LoggerFilter implements Filter {

    //private final Logger log = LoggerFactory.getLogger(LoggerFilter.class);

    /**
     * 应用名
     */
    private String appName;

    /**
     * 内容压缩
     */
    private ContentCompactor contentCompactor;

    public LoggerFilter(String appName) {
        this(appName, true);
    }

    public LoggerFilter(String appName, boolean enableCompactor) {
        this.appName = appName;

        if (enableCompactor) {
            contentCompactor = new CutoffContentCompactor();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse && !isStaticHttpRequet(servletRequest)) {
            HttpResponseWrapper wrapper = new HttpResponseWrapper((HttpServletResponse)servletResponse);
            HttpProcessObserver observer = new HttpProcessObserver(this.appName, contentCompactor);

            // 开始处理
            observer.beginProcess((HttpServletRequest)servletRequest);

            try {
                filterChain.doFilter(servletRequest, wrapper);
            } catch (Throwable ex) {
                // TODO 记录异常日志
                ex.printStackTrace();
                throw ex;
            }

            // 请求结束，处理
            observer.endProcess(wrapper);

            write(wrapper, observer);
        } else {
            //log.info("{}|{}", this.appName, System.currentTimeMillis());
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * 数据写入日志
     * @param wrapper
     * @param observer
     * @throws IOException
     */
    private void write(HttpResponseWrapper wrapper, HttpProcessObserver observer) throws IOException {
        try {
            // 记录日志 16个参数
            log.info("{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}", observer.buildMessageParams());
        } catch (Exception e) {
            //NO OP
        } finally {
            wrapper.copyBodyToResponse();
        }
    }

    /**
     * 判断是否为静态资源请求
     * @param request
     * @return
     */
    public boolean isStaticHttpRequet(ServletRequest request) {
        String uri = ((HttpServletRequest)request).getRequestURI();

        if(uri.matches(".*\\.(?i)(ico|gif|jpg|jpeg|png|css|js|txt|xml|swf|wav|txt|zip|doc|rar|gz)")) {
            return true;
        }

        return false;
    }

    @Override
    public void destroy() {

    }
}
