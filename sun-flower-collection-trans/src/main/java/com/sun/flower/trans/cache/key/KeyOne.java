package com.sun.flower.trans.cache.key;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/4/11 13:48
 **/
@Data
@ToString
public class KeyOne {

    @NotNull(message = "不允许为空")
    private Integer cat;

    private Integer page;

}
