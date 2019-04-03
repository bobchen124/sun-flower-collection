package com.sun.flower.support.compactor;

/**
 * @Desc: 实现类
 * @Author: chenbo
 * @Date: 2019/4/2 16:46
 **/
public class CutoffContentCompactor implements ContentCompactor {

    /**
     * 默认100
     */
    private static final int DEFAULT_CUTOFF_SIZE = 100;

    private int cutoffSize;

    public CutoffContentCompactor() {
        this(DEFAULT_CUTOFF_SIZE);
    }

    public CutoffContentCompactor(int cutoffSize) {
        this.cutoffSize = cutoffSize;
    }

    /**
     * 大于限制长度截取
     * @param content
     * @return
     */
    @Override
    public String compact(String content) {
        if (content != null && content.length() > cutoffSize) {
            return content.substring(0, cutoffSize);
        }

        return content;
    }

}
