package com.sun.flower.nlp.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.summary.TextRankKeyword;

import java.util.Collections;
import java.util.List;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/7/22 11:38
 **/
public class NLPTokenizerDemo {

    public static void main(String[] args) {
        //System.out.println(HanLP.extractSummary("【海外名品 己质检 直降2800】AX新品首发5D科技减震透气飞织 升级刀锋限量定制手绣版跑鞋 龙鳞白--限量版 41", 10));
        // System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
        // 注意观察下面两个“希望”的词性、两个“晚霞”的词性
        // System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
        // System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));

        String content = "GAP旗舰店 男装棉质打底短袖T恤基本款男士弹力圆领内搭logo上衣潮 354452 石楠灰 185/104A(L)";
        List<String> keywordList = HanLP.extractKeyword(content, 20);
        Collections.reverse(keywordList);
        System.out.println(keywordList);

        TextRankKeyword textRankKeyword = new TextRankKeyword();
        keywordList = textRankKeyword.getKeywords(content);
        System.out.println(keywordList);
    }

}
