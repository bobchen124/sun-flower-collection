package com.sun.flower.jetcache.service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheGetResult;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 09:33
 **/
@Component
@Slf4j
public class BaseCacheService {

    @CreateCache(name="s:jet:cache:", expire = 10, timeUnit = TimeUnit.MINUTES, cacheType = CacheType.REMOTE)
    public Cache<String, String> stringCache;

    /**
     *
     * @param arg
     * @return
     */
    public String getCache(String arg) {
        String key = "getCache:" + arg;

        CacheGetResult<String> cacheGetResult = stringCache.GET(key);
        log.info("code = {}, success = {}", cacheGetResult.getResultCode(), cacheGetResult.isSuccess());

        stringCache.put(key, "string cache get");
        return "string cache test";
    }

}
