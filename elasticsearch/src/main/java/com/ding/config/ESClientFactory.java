package com.ding.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName ESClientFactory
 * @date 2020-03-25 14:27
 */
public class ESClientFactory {
    private static Logger log = LoggerFactory.getLogger(ESClientFactory.class);
//    private static final String HOST = "127.0.0.1";
//    private static final int PORT = 9200;
//    private static final String SCHEMA = "http";
    private static  Integer CONNECT_TIME_OUT = 1000;
    private static  Integer SOCKET_TIME_OUT = 30000;
    private static  Integer CONNECTION_REQUEST_TIME_OUT = 500;

    private static  Integer MAX_CONN_TOTAL = 100;
    private static  Integer MAX_CONNECT_PER_ROUTE = 100;

    private static HttpHost HTTP_HOST;
    //连接超时
    private static boolean uniqueConnectTimeConfig = false;
    //连接数
    private static boolean uniqueConnectNumConfig = true;
    private static RestClientBuilder builder;
    //Java Low Level REST Client （要想使用高版本client必须依赖低版本的client）
    private static RestClient restClient;
    //Java High Level REST Client （高版本client）
    private static RestHighLevelClient restHighLevelClient;

    private static ESClientFactory esClientFactory = new ESClientFactory();

    private ESClientFactory(){};

//    static {
//        init();
//    }


    public static ESClientFactory build(HttpHost httpHost,Integer maxConnectNum, Integer maxConnectPerRoute){
        HTTP_HOST = httpHost;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONNECT_PER_ROUTE = maxConnectPerRoute;
        return  esClientFactory;
    }

    public static ESClientFactory build(HttpHost httpHost,Integer connectTimeOut, Integer socketTimeOut,
                                              Integer connectionRequestTime,Integer maxConnectNum, Integer maxConnectPerRoute){
        HTTP_HOST = httpHost;
        CONNECT_TIME_OUT = connectTimeOut;
        SOCKET_TIME_OUT = socketTimeOut;
        CONNECTION_REQUEST_TIME_OUT = connectionRequestTime;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONNECT_PER_ROUTE = maxConnectPerRoute;
        return  esClientFactory;
    }

    @PostConstruct
    public static void init(){
        log.info("ES info url:{}",HTTP_HOST);
        builder = RestClient.builder(HTTP_HOST);
        if (uniqueConnectTimeConfig)
            setConnectTimeOutConfig();
        if (uniqueConnectNumConfig)
            setConnectNumConfig();
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);
        log.info("ES init factory success!");
    }

    //主要关于异步httpclient的连接延时配置
    public static void setConnectTimeOutConfig(){
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(CONNECT_TIME_OUT);
                requestConfigBuilder.setSocketTimeout(SOCKET_TIME_OUT);
                requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
                return requestConfigBuilder;
            }
        });
    }
    //主要关于异步httpclient的连接数配置
    public static void setConnectNumConfig(){
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
                httpClientBuilder.setMaxConnPerRoute(MAX_CONNECT_PER_ROUTE);
                return httpClientBuilder;
            }
        });
    }

    public static RestClient getRestClient() {
        return restClient;
    }

    public static RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public static  void close(){
        log.info("ES success the close!");
        if (restClient != null){
            try {
                restClient.close();
            }catch (IOException e){
                log.error("ES close the error! because:{}",e);
            }
        }
    }
}
