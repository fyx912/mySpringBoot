package com.ding.test;

import com.alibaba.fastjson.JSON;
import com.ding.Application;
import com.ding.model.Book;
import com.ding.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class BookTest {
    @Autowired
    private BookRepository repository;

    @Test
    public void add(){
        List<Book> list = new ArrayList<>();
        list.add(new Book("1","Python网络爬虫从入门到实践","唐松,陈智铨",5,31520005," 科技 > 计算机/网络 > 程序设计" ,"机械工业出版社","2017-09-01"));
        list.add(new Book("2","Kafka权威指南","（美）妮哈·纳克海德（Neha Narkhede）,格温·沙皮拉（Gwen Shapira）,托德·帕利诺（Todd Palino）",5,24321500," 科技 > 计算机/网络 > 程序设计" ,"人民邮电出版社","2018-01-01"));
        list.add(new Book("3","深入理解JVM＆G1GC","周明耀",4,5302143," 科技 > 计算机/网络 > 程序设计" ,"电子工业出版社","2017-06-01"));
        list.add(new Book("4","编写高质量代码：Web前端开发修炼之道","曹刘阳",2,12530215,"科技 > 计算机/网络 > 多媒体/数据通信" ,"机械工业出版社","2010-06-01"));
        list.add(new Book("5","局外人","唐松,陈智铨",3,31520005," 科技 > 计算机/网络 > 程序设计" ,"机械工业出版社","2017-09-01"));
        list.add(new Book("6","百年孤独 马尔克斯代表作","加西亚·马尔克斯",5,31520005,"文艺 > 小说 > 世界名著" ,"机械工业出版社","2017-09-01"));
        list.add(new Book("7","黑名单上的守护者","唐松,陈智铨",4,31520005," 科技 > 计算机/网络 > 程序设计" ,"机械工业出版社","2017-09-01"));
        list.add(new Book("8","余罪：我的刑侦笔记(1-8册)","常书欣",5,31520005," 科技 > 计算机/网络 > 程序设计" ,"机械工业出版社","2017-09-01"));
        list.add(new Book("9","小李飞刀(套装共9册)","古龙",2,31520005," 科技 > 计算机/网络 > 程序设计" ,"文汇出版社","2017-09-01"));
        list.add(new Book("10","大秦帝国(套装共17卷)","孙皓晖",5,31520005,"文艺 > 小说 > 历史" ,"中信出版社","2017-09-01"));
        repository.saveAll(list);
    }

    @Test
    public void select(){
        //不分词查询 参数1： 字段名，参数2：字段查询值，因为不分词，所以汉字只能查询一个字，英语是一个单词
        QueryBuilder queryBuilder = QueryBuilders.termQuery("title","大秦帝国(套装共17卷)");
        log.info("精确查询:"+queryBuilder.toString());
        //分词查询，采用默认的分词器
        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("author", "古龙");
        log.info("精确查询:"+queryBuilder2.toString());
        ValueCountAggregationBuilder vcab = AggregationBuilders.count("wordCount").field("wordCount");
        log.info("统计:"+vcab);
        log.info("不分词查询:"+ JSON.toJSONString(repository.search(queryBuilder)));
    }
}
