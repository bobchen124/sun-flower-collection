package com.sun.flower.trans.aspect;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.sun.flower.trans.aspect.params.ParamDTO;
import com.sun.flower.trans.aspect.params.ResopneVO;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/26 18:15
 **/
@Service
@Data
public class LoadCacheService implements InitializingBean {

    LoadingCache<ParamDTO, List<ResopneVO>> listLoadingCache;

    @ServiceAnnotation(property = "listLoadingCache", pageLimit = 2)
    public List<ResopneVO> findResopneList(ParamDTO paramDTO) {
        List<ResopneVO> resopneVOS = Lists.newArrayList();

        ResopneVO resopneVO = new ResopneVO();
        resopneVO.setName("test-name-one" + paramDTO.getPage());
        resopneVO.setTitle("test-title-one");

        resopneVOS.add(resopneVO);

        ResopneVO resopneVO2 = new ResopneVO();
        resopneVO2.setName("test-name-one" + paramDTO.getPage());
        resopneVO2.setTitle("test-title-one");

        resopneVOS.add(resopneVO2);

        return resopneVOS;
    }

    @ServiceAnnotation(property = "listLoadingCache", pageLimit = 2)
    public List<ResopneVO> baseService(ParamDTO paramDTO) {
        throw new RuntimeException();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        listLoadingCache = CacheBuilder.newBuilder().initialCapacity(10)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build(new CacheLoader<ParamDTO, List<ResopneVO>>() {
                    @Override
                    public List<ResopneVO> load(ParamDTO key) throws Exception {
                        return findResopneList(key);
                    }
                });
    }

}
