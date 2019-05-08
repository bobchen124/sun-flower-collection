package com.sun.flower.common.exception;

import com.sun.flower.common.enmus.StateCode;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 14:11
 **/
public class RedisBusinessException extends BaseBusinessException {

    public RedisBusinessException(StateCode stateCode) {
        super(stateCode, null, null);
    }

    public RedisBusinessException(StateCode stateCode, Throwable ex, Object... msgArgs) {
        super(stateCode, ex, msgArgs);
    }
}
