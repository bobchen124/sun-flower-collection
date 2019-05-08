package com.sun.flower.jetcache.service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheGetResult;
import com.alicp.jetcache.CacheResultCode;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.flower.common.aspect.ServiceAnnotation;
import com.sun.flower.common.enmus.StateCode;
import com.sun.flower.common.exception.RedisBusinessException;
import com.sun.flower.common.exception.SystemBussinessException;
import com.sun.flower.jetcache.param.ParamDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 09:33
 **/
@Component
@Slf4j
public class BaseCacheService implements IBaseCacheService, InitializingBean {

    @CreateCache(name="s:jet:cache:", expire = 10, timeUnit = TimeUnit.MINUTES, cacheType = CacheType.REMOTE)
    Cache<String, String> stringCache;

    @Getter
    LoadingCache<String, String> localLoadingCache;

    /**
     *
     * @param arg
     * @return
     */
    @Override
    @ServiceAnnotation(property = "localLoadingCache")
    public String getCache(ParamDTO arg) throws RedisBusinessException, SystemBussinessException {
        String key = "getCache:" + arg.getPage();

        CacheGetResult<String> cacheGetResult = stringCache.GET(key);
        log.info("code = {}, success = {}", cacheGetResult.getResultCode(), cacheGetResult.isSuccess());

        if (CacheResultCode.FAIL == cacheGetResult.getResultCode()) {
            throw new RedisBusinessException(StateCode.B00001);
        }

        if (cacheGetResult.isSuccess()) {
            return cacheGetResult.getValue();
        }

        if ("10".equals(arg.getCat().toString())) {
            throw new SystemBussinessException(StateCode.B00001);
        }

        stringCache.put(key, "string cache get");
        return "string cache test";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        localLoadingCache = CacheBuilder.newBuilder().build(
                new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "";
                    }
                }
        );
    }

}
