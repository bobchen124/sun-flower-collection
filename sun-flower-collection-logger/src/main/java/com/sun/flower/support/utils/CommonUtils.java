package com.sun.flower.support.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/2 17:46
 **/
public class CommonUtils {

    /**
     *
     * @param content
     * @return
     */
    public static String ignoreInvalidCharacters(String content) {
        if (content != null && content.length() != 0) {
            return content.replaceAll("[\r\n\t\f|:\\$]", "*");
        }

        return content;
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        if (null == request) {
            return "";
        }

        String proxs[] = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip = "";

        for (String prox : proxs) {
            ip = request.getHeader(prox);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                ip = ip.split(",")[0];
            }
        }

        if (ip == null || ip.isEmpty()) {
            return request.getRemoteAddr();
        }

        return ip;
    }

}
