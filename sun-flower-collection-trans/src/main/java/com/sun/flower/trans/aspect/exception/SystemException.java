package com.sun.flower.trans.aspect.exception;

import lombok.Data;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/28 09:27
 **/
@Data
public class SystemException extends BaseException {

    public SystemException(String errorCode, String erroMessage) {
       super(errorCode, erroMessage);
    }

}
