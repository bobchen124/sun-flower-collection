package com.sun.flower.common.exception;

import com.sun.flower.common.enmus.StateCode;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 14:13
 **/
public class SystemBussinessException extends BaseBusinessException {

    public SystemBussinessException(StateCode stateCode) {
        super(stateCode, null, null);
    }

    public SystemBussinessException(StateCode stateCode, Throwable ex, Object... msgArgs) {
        super(stateCode, ex, msgArgs);
    }

}
