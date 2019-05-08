package com.sun.flower.common.exception;

import com.sun.flower.common.enmus.StateCode;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 14:06
 **/
public abstract class BaseBusinessException extends Exception {

    /**
     * 异常Code
     */
    private String errorCode;

    /**
     * 异常信息
     */
    private String erroMessage;

    /**
     * 异常堆栈
     */
    private String exStackTrace;

    public BaseBusinessException(StateCode stateCode, Throwable ex, Object... msgArgs) {
        if (stateCode != null) {
            this.errorCode = stateCode.toString();

            if (msgArgs != null) {
                this.erroMessage = String.format(stateCode.getMsg(), msgArgs);
            } else {
                this.erroMessage = stateCode.getMsg();
            }

            if (ex != null) {
                this.exStackTrace = ex.getMessage();
            }
        }
    }

}
