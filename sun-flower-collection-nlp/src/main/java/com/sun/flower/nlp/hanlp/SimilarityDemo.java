package com.sun.flower.nlp.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.suggest.Suggester;

import java.util.List;

/**
 * @Desc: 文本相似度
 * @Author: chenbo
 * @Date: 2019/7/22 14:45
 **/
public class SimilarityDemo {

    public static void main(String[] args) {
        String document = "【海外名品 己质检 直降2800】AX新品首发5D科技减震透气飞织 升级刀锋限量定制手绣版跑鞋 龙鳞白--限量版 41";
        List<String> sentenceList = HanLP.extractPhrase(document, 3);
        List<String> keywordList = HanLP.extractKeyword(document, 5);
        System.out.println(keywordList);

        //System.out.println("------" + sentenceList.size());
        sentenceList.forEach(s -> System.out.println(s));

        // 文本推荐
        Suggester suggester = new Suggester();
        String[] titleArray = (
                        "威廉王子发表演说 呼吁保护野生动物\n" +
                                "《时代》年度人物最终入围名单出炉 普京马云入选\n" +
                                "“黑格比”横扫菲：菲吸取“海燕”经验及早疏散\n" +
                                "日本保密法将正式生效 日媒指其损害国民知情权\n" +
                                "【海外名品 己质检 直降2800】AX新品首发5D科技减震透气飞织 升级刀锋限量定制手绣版跑鞋 龙鳞白--限量版 41”"
                ).split("\\n");

        for (String title : titleArray) {
            suggester.addSentence(title);
        }

        // 语义
        System.out.println(suggester.suggest("发言", 1));
        // 字符
        System.out.println(suggester.suggest("AX新品首发5D科技减震透气飞织", 1));
        // 拼音
        System.out.println(suggester.suggest("mayun", 1));

    }

}
