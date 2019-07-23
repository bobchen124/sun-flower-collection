package com.sun.flower.nlp.hanlp;

import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;

/**
 * @Desc:
 * @Author: chenbo
 * @Date: 2019/7/22 15:58
 **/
public class DemoWordDistance {

    public static void main(String[] args) {
        String a = "衬衫";
        String b = "短袖衬衫";

        System.out.printf("%-5s\t%-5s\t%-15d\t%-5.10f\n", "", "",
                CoreSynonymDictionary.distance(a, b),
                CoreSynonymDictionary.similarity(a, b));
    }

}
