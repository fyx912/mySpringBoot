package com.ding.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName CacheConfig
 * @date 2020-11-12 13:49
 */
@Configuration
@EnableCaching //开启缓存，可以放在启动类上
public class CacheConfig {
    /**
     * 自定义KeyGenerator。
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            //获取代理对象的最终目标对象
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
            StringBuilder sb = new StringBuilder();
            sb.append(targetClass.getSimpleName()).append(":");
            sb.append(method.getName()).append(":");
            //调用SimpleKey的逻辑
            Object key = SimpleKeyGenerator.generateKey(params);
            return sb.append(key);
        };
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //设置特有的Redis配置
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        //默认超时时间60s
        return RedisCacheManager.builder(connectionFactory).
                transactionAware().   //Cache的事务支持
                cacheDefaults(customRedisCacheConfiguration(Duration.ofSeconds(60*2))).
                withInitialCacheConfigurations(cacheConfigurations).   //设置个性化的Cache配置
                build();
    }



    /**
     * 设置RedisConfiguration配置
     *
     * @param ttl
     * @return
     */
    public RedisCacheConfiguration customRedisCacheConfiguration(Duration ttl) {
        //设置序列化格式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer
                = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 解决jackson2无法反序列化LocalDateTime的问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return RedisCacheConfiguration.
                defaultCacheConfig().serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).
                computePrefixWith(cacheName -> cacheName + ":").   //设置Cache的前缀，默认::
                disableCachingNullValues().   //若返回值为null，则不允许存储到Cache中
                entryTtl(ttl);  //设置缓存缺省超时时间
    }

}
