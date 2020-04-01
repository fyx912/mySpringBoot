package com.ding.utils;

import com.alibaba.fastjson.JSONObject;
import com.ding.exception.ServiceException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
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
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName EsIndexUtil
 * @date 2020-03-25 19:28
 */


@Service("esUtilService")
@Slf4j
public class EsUtilServiceImpl implements EsUtilService{

    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean checkIndexExist(String index){
        GetIndexRequest request = new GetIndexRequest(index);
//         ActionListener listener = new ActionListener<Boolean>() {
//            @Override
//            public void onResponse(Boolean aBoolean) {
//                log.info("es index Exist success ! result:{}",aBoolean);
//            }
//            @Override
//            public void onFailure(Exception e) {
//                log.error("es  index Exist  failed !error because:{}",e);
//            }
//        };
//        Cancellable call =  client.indices().existsAsync(request, RequestOptions.DEFAULT,listener);
        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean create(String index) {
        if (checkIndexExist(index)){
            CreateIndexRequest indexRequest = new CreateIndexRequest(index);
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
                indexRequest.source(builder);
                builder.flush();
                CreateIndexResponse createIndexResponse =  client.indices().create(indexRequest, RequestOptions.DEFAULT);
                System.out.println(createIndexResponse.isAcknowledged());
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                builder.close();
            }
        }
        return false;
    }

    @Override
    public boolean update(String index) {
        return false;
    }

    @Override
    public boolean delete(String index) throws IOException {
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse response = client.indices().delete(deleteRequest,RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public long count(String index){
        long count ;
        SearchRequest searchRequest = new SearchRequest(index);
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = response.getHits();
            searchHits.getTotalHits();
            count = searchHits.getTotalHits().value;
        }catch (IOException e){
            log.error("获取{}总条数异常,because:{}",index,e);
            throw new ServiceException("获取数据异常",e);
        }
        return count;
    }

    public List select(String index, String fieldKey, Object value, Integer startRow, Integer size){
        List list = null;
        if (startRow==null){
            startRow = 0;
        }
        if (size==null){
            size = 10;
        }
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(fieldKey, value);
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);//模糊匹配

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(matchQueryBuilder);
        builder.sort("_score",SortOrder.DESC);
        builder.profile(true); //分析结果
        builder.from(startRow);//开始行
        // 获取记录数，默认10
        builder.size(size);

        //设置查询的结果高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        builder.highlighter(highlightBuilder);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(builder);
        try {
            list = new ArrayList();

            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(JSONObject.toJSON(response.getProfileResults()));
            System.out.println(JSONObject.toJSON(response.getHits()));

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
        }catch (IOException e){
            log.error("获取{}数据异常,because:{}",index,e);
            throw new ServiceException("获取数据异常",e);
        }
        return list;
    }

}
