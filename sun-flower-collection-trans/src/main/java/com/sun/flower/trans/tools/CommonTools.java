package com.sun.flower.trans.tools;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @Desc: 通用工具类
 * @Author: chenbo
 * @Date: 2019/4/3 15:33
 **/
@Slf4j
public class CommonTools {

    /**
     * 分隔符-冒号
     */
    public static final String SEPARATOR_COLON = ":";

    /**
     * 根据对象属性生成key
     * @param target
     * @return
     */
    public String generateKeyFromProperty(Object target) {
        return this.generateKeyFromProperty(target, SEPARATOR_COLON, Lists.newArrayList());
    }

    /**
     * 根据对象属性生成key
     * @param target 目标
     * @param ignoreProperties 需要过滤的字段属性
     * @return
     */
    public String generateKeyFromProperty(Object target, String separator, Collection<String> ignoreProperties) {
        // 获得对象属性
        Field[] fields = target.getClass().getDeclaredFields();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0, len = fields.length; i < len; i++) {
            // 如果是屏蔽直接过滤
            if (ignoreProperties != null && ignoreProperties.contains(fields[i].getName())) {
                continue;
            }

            stringBuilder.append(fields[i].getName()).append(separator);
        }

        log.info("generateKeyFromProperty key = {}", stringBuilder.toString());
        return stringBuilder.toString();
    }

}
