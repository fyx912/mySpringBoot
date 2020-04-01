package com.ding.test;

import com.alibaba.fastjson.JSONObject;
import com.ding.Application;
import com.ding.utils.EsUtilService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class BookTest {

    @Autowired
    private RestHighLevelClient highLevelClient;
    @Autowired
    private EsUtilService service;

    @Test
    public void index(){
        CreateIndexRequest index = new CreateIndexRequest("book");
        Map<String,Object> properties = Maps.newHashMap();
        Map<String,Object> propertie = Maps.newHashMap();
        propertie.put("type","text");
        propertie.put("index",true);
        propertie.put("analyzer","ik_max_word");
        propertie.put("search_analyzer","ik_max_word");
        properties.put("field_name",propertie);
        XContentBuilder builder =null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
//                builder.startObject("mappings");
//                {
//                    builder.startObject("index_name");
//                    {
//                        builder.field("properties",properties);
//                    }
//                    builder.endObject();
//                }
//                builder.endObject();
                builder.startObject("settings");
                {
                    builder.field("index.analysis.analyzer.default.type","ik_max_word");
                    builder.field("number_of_shards",1);
                    builder.field("number_of_replicas",1);
                 }
                builder.endObject();
            }
            builder.endObject();
            System.out.println(builder.toString());
            index.source(builder);
            builder.flush();
            CreateIndexResponse createIndexResponse =  highLevelClient.indices().create(index,RequestOptions.DEFAULT);
            System.out.println(createIndexResponse);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            builder.close();
        }
    }

    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest index = new DeleteIndexRequest("book");
        AcknowledgedResponse response = highLevelClient.indices().delete(index,RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }


    @Test
    public void checkIndexExist() throws IOException {
        GetIndexRequest request = new GetIndexRequest("book");
//        boolean exist =  highLevelClient.indices().exists(request, RequestOptions.DEFAULT);
//        System.out.println(exist);

       ActionListener  listener =  new ActionListener<Boolean>() {

           @Override
           public void onResponse(Boolean aBoolean) {
                System.out.println("onResponse===="+aBoolean);
           }
           @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        };
        highLevelClient.indices().existsAsync(request, RequestOptions.DEFAULT,listener);

        System.out.println("listener==="+listener);
    }

    @Test
    public void checkIndexExistIpml(){
        boolean exist =  service.checkIndexExist("book");
        System.out.println(exist);
    }

    @Test
    public void select() throws IOException {
        int startRow =0;
        int size = 10;

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("content", "体系");
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);//模糊匹配

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(matchQueryBuilder);
        builder.profile(true); //分析结果
        builder.from(startRow);//开始行
        // 获取记录数，默认10
        builder.size(size);

        //设置查询的结果高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        builder.highlighter(highlightBuilder);

        SearchRequest searchRequest = new SearchRequest("book");
        searchRequest.source(builder);
        SearchResponse response= highLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response.getProfileResults()));

        System.out.println(JSONObject.toJSON(response.getHits()));

        List list = new ArrayList();

        SearchHits hits = response.getHits();
        for (SearchHit hit : hits.getHits()) {

            Map<String, Object> source = hit.getSourceAsMap();

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField nameField = highlightFields.get("content");
            if(nameField!=null){
                Text[] fragments = nameField.fragments();
                StringBuilder nameTmp = new StringBuilder();
                for(Text text:fragments){
                    nameTmp.append(text);
                }
                //将高亮片段组装到结果中去
                source.put("title", nameTmp.toString());
            }
            list.add(source);

        }
        System.out.println(JSONObject.toJSON(list));
    }

    @Test
    public void analyzeWords() throws IOException {
        AnalyzeRequest analyzeRequest = AnalyzeRequest.withField(
                "book","content","Python网络爬虫从入门到实践");
        AnalyzeResponse response = highLevelClient.indices().analyze(analyzeRequest, RequestOptions.DEFAULT);

        System.out.println(JSONObject.toJSON(response.getTokens()));
    }

//    @Test
//    public void add(){
//        List<Book> list = new ArrayList<>();
//        list.add(new Book("1","Python网络爬虫从入门到实践","唐松,陈智铨",5,31520005," 科技 > 计算机/网络 > 程序设计" ,"机械工业出版社",,"2017-09-01"));
//        list.add(new Book("2","Kafka权威指南","（美）妮哈·纳克海德（Neha Narkhede）,格温·沙皮拉（Gwen Shapira）,托德·帕利诺（Todd Palino）",5,24321500," 科技 > 计算机/网络 > 程序设计" ,"人民邮电出版社",,"2018-01-01"));
//        list.add(new Book("3","深入理解JVM＆G1GC","周明耀",4,5302143," 科技 > 计算机/网络 > 程序设计" ,"电子工业出版社",,"2017-06-01"));
//        list.add(new Book("4","编写高质量代码：Web前端开发修炼之道","曹刘阳",2,12530215,"科技 > 计算机/网络 > 多媒体/数据通信" ,"机械工业出版社",,"2010-06-01"));
//        list.add(new Book("5","局外人","唐松,陈智铨",3,31520005," 科技 > 计算机/网络 > 程序设计" ,"机械工业出版社",,"2017-09-01"));
//        list.add(new Book("6","百年孤独 马尔克斯代表作","加西亚·马尔克斯",5,31520005,"文艺 > 小说 > 世界名著" ,"机械工业出版社",,"2017-09-01"));
//        list.add(new Book("7","黑名单上的守护者","唐松,陈智铨",4,31520005," 科技 > 计算机/网络 > 程序设计" ,"机械工业出版社",,"2017-09-01"));
//        list.add(new Book("8","余罪：我的刑侦笔记(1-8册)","常书欣",5,31520005," 科技 > 计算机/网络 > 程序设计" ,"机械工业出版社",,"2017-09-01"));
//        list.add(new Book("9","小李飞刀(套装共9册)","古龙",2,31520005," 科技 > 计算机/网络 > 程序设计" ,"文汇出版社",,"2017-09-01"));
//        list.add(new Book("10","大秦帝国(套装共17卷)","孙皓晖",5,31520005,"文艺 > 小说 > 历史" ,"中信出版社",,"2017-09-01"));
////        repository.saveAll(list);
//    }

//    @Test
//    public void select(){
//        //不分词查询 参数1： 字段名，参数2：字段查询值，因为不分词，所以汉字只能查询一个字，英语是一个单词
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("title","大秦帝国(套装共17卷)");
//        log.info("精确查询:"+queryBuilder.toString());
//        //分词查询，采用默认的分词器
//        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("author", "古龙");
//        log.info("精确查询:"+queryBuilder2.toString());
//        ValueCountAggregationBuilder vcab = AggregationBuilders.count("wordCount").field("wordCount");
//        log.info("统计:"+vcab);
//        log.info("不分词查询:"+ JSON.toJSONString(repository.search(queryBuilder)));
//    }
}
