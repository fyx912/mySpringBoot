package com.ding.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册中心配置
 * 用于注册和协调作业分布式行为的组件，目前仅支持Zookeeper。
*/
@Configuration
@ConditionalOnExpression("'${elasticJob.zookeeper.serverList}'.length()>0")
public class RegistryCenterConfig {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${elasticJob.zookeeper.serverList}") final String serverList, @Value("${elasticJob.zookeeper.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }

//    /**
//     * 设置活动监听，前提是已经设置好了监听
//     * @return
//     */
////    @Bean
//    public ElasticJobListener elasticJobListener(){
//        return  new ElasticJobListener(1000,1000);
//    }

}
