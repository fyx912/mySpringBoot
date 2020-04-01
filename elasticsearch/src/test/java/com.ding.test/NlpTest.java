package com.ding.test;

import com.hankcs.hanlp.HanLP;

import java.util.List;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName NlpTest
 * @date 2020-03-26 15:12
 */
public class NlpTest {
    public static void main(String[] args){
        String content = "公共卫生事件应急管理，关键是要在源头上进行防范，这既包括体系，也包括环境和观念等层面。尤其是在公共卫生事件应急管理体系建设上，要把各种规章制度转化成人们的自觉行为，深入人心。";
        List<String> keywordList = HanLP.extractKeyword(content, 40);
        System.out.println(keywordList);
    }
}
