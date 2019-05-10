package com.sun.flower.jetcache.service;


import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheGetResult;
import com.alicp.jetcache.CacheResultCode;
import com.sun.flower.common.enmus.StateCode;
import com.sun.flower.common.exception.RedisBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Desc: 增加redis异常处理，get操作无法感知redis异常，封装GET操作
 * @Author: chenbo
 * @Date: 2019/5/9 10:12
 **/
@Slf4j
@Component
public class JetCacheObserver {

    /**
     * 封装jetcahce GET操作，增加redis异常返回FAIL 处理逻辑
     * @param cache
     * @param key
     * @param <T> 返回值，null没有缓存值
     * @return
     */
    public <T> T get(Cache<String, T> cache, String key) throws RedisBusinessException {
        CacheGetResult<T> cacheGetResult = cache.GET(key);

        // 返回FAIL，redis连接超时或者连接断开
        if (cacheGetResult.getResultCode() == CacheResultCode.FAIL) {
            log.info("redis connection is timeout or shutdown, resultCode = {}", cacheGetResult.getResultCode());
            throw new RedisBusinessException(StateCode.B00001);
        }

        return (cacheGetResult.isSuccess()) ? cacheGetResult.getValue() : null;
    }

}
