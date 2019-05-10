package com.sun.flower.jetcache.service;

import com.sun.flower.common.exception.BaseBusinessException;
import com.sun.flower.jetcache.param.ParamDTO;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 20:23
 **/
public interface IBaseCacheService {

    String getCache(ParamDTO arg) throws Exception;

    String getCache2(ParamDTO arg) throws BaseBusinessException;

}
