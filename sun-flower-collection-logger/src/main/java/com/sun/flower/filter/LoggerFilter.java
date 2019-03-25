package com.sun.flower.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/3/22 19:22
 **/
@Slf4j
public class LoggerFilter implements Filter {

    private String appName;

    public LoggerFilter(String appName) {
        this.appName = appName;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("-----" + appName);
    }

    @Override
    public void destroy() {

    }
}
