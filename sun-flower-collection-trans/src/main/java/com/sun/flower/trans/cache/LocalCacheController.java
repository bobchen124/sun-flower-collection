package com.sun.flower.trans.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.sun.flower.trans.cache.key.KeyOne;
import com.sun.flower.trans.cache.key.KeyTwo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/11 13:45
 **/
@Slf4j
@RestController
@RequestMapping("/api/cache")
public class LocalCacheController implements InitializingBean {

    @Autowired
    LocalCacheService localCacheService;

    LoadingCache<KeyOne, List<String>> keyOneListCache;

    LoadingCache<String, List<String>> stringLoadingCache;

    RemovalListener<Object, List<String>> removalListener;

    @PostMapping("/one")
    public List<String> findByKeyOne(@Valid KeyOne keyOne) throws Exception {
        List<String> stringList = keyOneListCache.get(keyOne);
        return stringList;
    }

    @RequestMapping("/two")
    public List<String> findByKeyOne(KeyTwo keyTwo) {
        WeakReference<List<String>> stringList = new WeakReference(localCacheService.getByKeyTwo(keyTwo));
        return stringList.get();
    }

    @GetMapping("/str")
    public List<String> findByStr(String keyOne) throws Exception {
        List<String> stringList = stringLoadingCache.get(keyOne);
        return stringList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        removalListener = new RemovalListener<Object, List<String>>() {
            @Override
            public void onRemoval(RemovalNotification<Object, List<String>> removalNotification) {
                log.info("onRemoval, key = {}", removalNotification.getKey());
            }
        };

        // 本地缓存
        keyOneListCache = CacheBuilder.newBuilder().initialCapacity(10)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .maximumSize(100)
                .removalListener(removalListener)
                .build(new CacheLoader<KeyOne, List<String>>() {
                    @Override
                    public List<String> load(KeyOne keyOne) throws Exception {
                        log.info("from keyOne CacheLoader, {}, {}", keyOneListCache.size());
                        return localCacheService.getByKeyOne(keyOne);
                    }
                });

        stringLoadingCache = CacheBuilder.newBuilder().initialCapacity(2)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .maximumSize(5)
                .removalListener(removalListener)
                .build(new CacheLoader<String, List<String>>() {
                    @Override
                    public List<String> load(String s) throws Exception {
                        log.info("from str CacheLoader, {}", stringLoadingCache.size());
                        return localCacheService.getByKeyStr(s);
                    }
                });
    }
}
