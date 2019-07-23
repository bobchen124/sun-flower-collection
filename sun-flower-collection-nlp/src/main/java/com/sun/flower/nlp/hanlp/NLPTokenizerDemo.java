package com.sun.flower.nlp.hanlp;

import com.hankcs.hanlp.HanLP;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/7/22 11:38
 **/
public class NLPTokenizerDemo {

    public static void main(String[] args) {
        System.out.println(HanLP.extractSummary("【海外名品 己质检 直降2800】AX新品首发5D科技减震透气飞织 升级刀锋限量定制手绣版跑鞋 龙鳞白--限量版 41", 10));
        // System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
        // 注意观察下面两个“希望”的词性、两个“晚霞”的词性
        // System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
        // System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));
    }

}
