package com.sun.flower.trans.aspect.exception;

import lombok.Data;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/28 09:26
 **/
@Data
public class BaseException extends Exception {

    /**
     * 异常Code
     */
    private String errorCode;

    /**
     * 异常信息
     */
    private String erroMessage;

    public BaseException(String errorCode, String erroMessage) {
        this.errorCode = errorCode;
        this.erroMessage = erroMessage;
    }

}
