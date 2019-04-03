package com.sun.flower.support.compactor;

/**
 * @Desc: 内容是否压缩
 * @Author: chenbo
 * @Date: 2019/4/2 16:44
 **/
public interface ContentCompactor {

    /**
     * 压缩内容
     * @param content
     * @return
     */
     String compact(String content);
}
