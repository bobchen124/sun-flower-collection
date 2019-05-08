package com.sun.flower.common.enmus;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/5/8 14:07
 **/
public enum StateCode {

    /**
     * 成功,默认
     */
    B00000("请求成功"),

    /**
     * 失败
     */
    B00001("请求失败");

    public String getMsg() {
        return msg;
    }

    private String msg;

    StateCode(String msg) {
        this.msg = msg;
    }

}
