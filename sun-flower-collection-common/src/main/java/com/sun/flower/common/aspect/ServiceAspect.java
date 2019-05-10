package com.sun.flower.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.sun.flower.common.enmus.StateCode;
import com.sun.flower.common.exception.BaseBusinessException;
import com.sun.flower.common.exception.RedisBusinessException;
import com.sun.flower.common.exception.SystemBussinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/26 16:43
 **/
@Aspect
@Component
@Order(2)
@Slf4j
public class ServiceAspect {

    private final String PAGE_NAME = "page";

    @Pointcut("@annotation(com.sun.flower.common.aspect.ServiceAnnotation)")
    public void cacheMethod() {

    }

    @Around("cacheMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws BaseBusinessException {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        // 目标代理类
        Class<?> targetClazz = methodSignature.getDeclaringType();
        // 目标代理方法
        Method targetMethod = methodSignature.getMethod();

        // 获取注解实例, 各个参数值
        ServiceAnnotation annotation = targetMethod.getAnnotation(ServiceAnnotation.class);
        String property = annotation.property();
        int pageLimit = annotation.pageLimit();
        log.info("property = {}, pageLimit = {}", property, pageLimit);

        // 目标方法参数
        Object[] targetArgs = joinPoint.getArgs();
        Object targetObj = joinPoint.getTarget();

        // 获取 LoadingCache 属性
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(targetClazz, property);
        Method propertyReadMethod = descriptor.getReadMethod();

        // 第一个参数中 page的值，没有返回0
        JSONObject paramJsonObj = JSONObject.parseObject(JSONObject.toJSONString(targetArgs[0]));
        Integer pageNum = paramJsonObj.containsKey(PAGE_NAME)? paramJsonObj.getInteger(PAGE_NAME) : 0;
        log.info("pageNum = {}", pageNum);

        // 目标属性class
        Class<?> targetPropertyClazz = descriptor.getPropertyType();

        try {
            Object targetRet = joinPoint.proceed(targetArgs);

            // 缓存业务返回值
            if (pageLimit == 0 || ( pageLimit > 0 && pageLimit >= pageNum)) {
                // 目标属性对象
                Object tartgetPropertyObj = propertyReadMethod.invoke(targetObj);
                Method putMethod = targetPropertyClazz.getMethod("put", Object.class, Object.class);
                putMethod.invoke(tartgetPropertyObj, targetArgs[0], targetRet);
                log.info("local cache put success");
            }

            return targetRet;
        } catch (RuntimeException|RedisBusinessException re) {
            log.info("RuntimeException|RedisBusinessException , {}", re.getMessage());
            // 运行时异常，比如redis连接超时，空指针异常等，返回本地缓存对象
            try {
                Object tartgetPropertyObj = propertyReadMethod.invoke(targetObj);
                Method getMethod = targetPropertyClazz.getDeclaredMethod("get", Object.class);

                // 超出页数的，默认显示第一页
                if (paramJsonObj.containsKey(PAGE_NAME) && pageNum > pageLimit) {
                    Class<?> argClazz = targetArgs[0].getClass();
                    PropertyDescriptor argPageDescriptor = BeanUtils.getPropertyDescriptor(argClazz, PAGE_NAME);
                    Method pageWriteMethod = argPageDescriptor.getWriteMethod();
                    pageWriteMethod.invoke(targetArgs[0], 1);
                }
                log.info("Exception get val form local cache");
                return getMethod.invoke(tartgetPropertyObj, targetArgs[0]);
            } catch (Exception ex) {
                throw new SystemBussinessException(StateCode.B00001);
            }
        } catch (SystemBussinessException e) {
            // 自定义业务异常，原样抛出
            throw e;
        } catch (Throwable a) {
            throw new SystemBussinessException(StateCode.B00001);
        }
    }

}
