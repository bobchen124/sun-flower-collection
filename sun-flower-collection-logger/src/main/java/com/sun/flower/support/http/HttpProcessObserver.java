package com.sun.flower.support.http;

import com.alibaba.fastjson.JSONObject;
import com.sun.flower.support.compactor.ContentCompactor;
import com.sun.flower.support.utils.CommonUtils;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 日志采集处理类，负责处理Request和Response之间的处理时间、响应码等信息，并生成质量日志所需要的数组格式
 * @Author: chenbo
 * @Date: 2019/4/2 17:02
 **/
@Data
public class HttpProcessObserver {

    /**
     * 编码
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 类型-质量日志
     */
    private static final String LOG_TYPE_QUALITY = "QUALITY";

    /**
     * 协议
     */
    private static final String COMMUNICATION_TYPE_HTTP = "HTTP";

    private String appName;

    /**
     * 开始时间
     */
    private long beginTime;

    /**
     * 结束时间
     */
    private long endTime;

    /**
     * 请求处理时长
     */
    private long duration;

    /**
     * uri资源
     */
    private String uri;

    /**
     * http方法
     */
    private String httpMethod;

    private String clientInfo;

    private String serverInfo;

    /**
     * 请求参数
     */
    private Map<String, String> requestParams = new HashMap<String, String>();

    /**
     * 请求参数字符串
     */
    private String requestContent;

    /**
     * 请求数据长度
     */
    private long requestContentLength;

    /**
     * 返回内容
     */
    private String responseContent;

    /**
     * 响应数据长度
     */
    private long responseContentLength;

    private int httpStatus;

    private String responseContentType;

    private ContentCompactor compactor;

    //private TraceInfoVo traceInfo;

    private String oldMethod;

    /**
     * 构造函数
     * @param compactor
     */
    public HttpProcessObserver(String appName, ContentCompactor compactor) {
        this.appName = appName;
        this.compactor = compactor;
    }

    /**
     * 获取请求参数，写入map中
     * @param request
     */
    private void initRequestParams(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();

        while (params.hasMoreElements()) {
            String k = params.nextElement();
            String v = getParameter(request, k);

            this.requestParams.put(k, CommonUtils.ignoreInvalidCharacters(v));
        }
    }

    /**
     * 获取请求中指定参数的值
     * @param request
     * @param name
     * @return
     */
    private String getParameter(HttpServletRequest request, String name) {
        try {
            String v = request.getParameter(name);
            if (v != null && v.length() > 0) {
                String requestEncoding = request.getCharacterEncoding() != null ? request.getCharacterEncoding() : DEFAULT_ENCODING;
                return new String(v.getBytes(requestEncoding),  DEFAULT_ENCODING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 处理HttpServletRequest请求
     * @param request
     */
    public void beginProcess(HttpServletRequest request) {
        this.beginTime = System.currentTimeMillis();

        this.clientInfo = CommonUtils.getIP(request) + ":" + request.getRemotePort();
        this.serverInfo = request.getLocalAddr() + ":" + request.getLocalPort();

        this.uri = request.getRequestURI();
        this.httpMethod = request.getMethod();

        this.initRequestParams(request);
        this.requestContent = JSONObject.toJSONString(this.requestParams);
        this.requestContentLength = this.requestContent.length();
    }

    /**
     * 处理HttpResponse请求
     * @param responseWrapper
     */
    public void endProcess(HttpResponseWrapper responseWrapper) {
        this.endTime = System.currentTimeMillis();
        this.duration = endTime - beginTime;

        this.httpStatus = responseWrapper.getStatus();
        this.responseContentType = responseWrapper.getContentType();

        this.responseContentLength = responseWrapper.getContentLength();
        this.responseContent = responseWrapper.getContent(responseWrapper.getCharacterEncoding());

        if (this.compactor != null && this.responseContent != null) {
            //this.content = (String)CommonUtils.trimContent(this.content, this.compactor);
        }
    }

    /**
     * 处理响应内容，去除不合规则字符
     * @param content
     * @return
     */
    public String trimContent(String content) {
        try {
            content = CommonUtils.ignoreInvalidCharacters(content);

            if (this.compactor != null) {
                content = compactor.compact(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 生成质量日志所需要的日志内容数组
     * @return
     */
    public Object[] buildMessageParams() {
        return new Object[] {
                this.appName,
                LOG_TYPE_QUALITY,
                COMMUNICATION_TYPE_HTTP,
                this.clientInfo,
                this.serverInfo,
                this.beginTime,
                this.endTime,
                this.duration,
                this.uri,
                this.httpStatus,
                this.httpMethod,
                this.responseContentType,
                this.requestContentLength,
                this.responseContentLength,
                trimContent(this.requestContent),
                trimContent(this.responseContent)
        };
    }
}
