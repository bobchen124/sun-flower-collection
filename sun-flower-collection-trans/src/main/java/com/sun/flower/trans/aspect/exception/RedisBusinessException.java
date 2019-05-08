package com.sun.flower.trans.aspect.exception;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 13:59
 **/
public class RedisBusinessException extends BaseException {

    public RedisBusinessException(String errorCode, String erroMessage) {
        super(errorCode, erroMessage);
    }

}
