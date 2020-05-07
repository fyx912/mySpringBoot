package com.ding.config;


import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName ESConfig
 * @date 2020-03-25 15:22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "es", ignoreUnknownFields = true)
public class ESConfig {
    private  String host = "192.168.0.168";
    private  int port = 12000;
    private  String scheme = "http";
    private  Integer connectNum=100;
    private  Integer connectPerRoute=100;



    @Bean
    public  HttpHost httpHost(){
        return new HttpHost(host,port,scheme);
    }

    @PostConstruct
    @Bean(initMethod = "init",destroyMethod = "close")
    public  ESClientFactory getFactory(){
        return ESClientFactory.build(httpHost(),connectNum,connectPerRoute) ;
    }

    @PostConstruct
    @Bean
    @Scope("singleton")
    public  RestClient getRestClient(){
        return ESClientFactory.getRestClient();
    }

    @PostConstruct
    @ConditionalOnMissingBean
    @Bean(name = "restHighLevelClient")
    @Scope("singleton")
    public  RestHighLevelClient getRestHighLevelClient(){
        return ESClientFactory.getRestHighLevelClient();
    }



//
//    @Bean("esUtilService")
//    @ConditionalOnMissingBean
//    @DependsOn(value = "restHighLevelClient")
//    public EsUtilService esUtilService(){
//        return  new EsUtilServiceImpl();
//    }

}
