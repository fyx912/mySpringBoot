package com.ding.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

//般用作父类的repository，有这个注解，spring不会去实例化该repository。
@NoRepositoryBean
public interface BaseElasticsearch<T,ID extends Serializable> extends ElasticsearchRepository<T,ID> {
}
