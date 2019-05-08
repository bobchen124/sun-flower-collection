package com.sun.flower.common.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/26 16:21
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceAnnotation {

    /**
     * 属性名
     * @return
     */
    String property() default "";

    /**
     * 页数限制，0代表不限制
     * @return
     */
    int pageLimit() default 0;

}
