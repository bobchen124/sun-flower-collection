package com.sun.flower.filter;

import com.sun.flower.support.compactor.ContentCompactor;
import com.sun.flower.support.compactor.CutoffContentCompactor;
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
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/3/22 19:22
 **/
@Slf4j
public class LoggerFilter implements Filter {

    //Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

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
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse && isStaticHttpRequet(servletRequest)) {
            log.info("-----" + appName);

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
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
