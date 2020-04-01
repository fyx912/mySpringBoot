package com.ding.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.util.Date;

/**
 * elasticsearch 中：
 * indexName:索引库的名称，建议以项目的名称命名，就相当于数据库DB
 * type –> 类型，建议以实体的名称命名Table ，就相当于数据库中的表table
 * Document –> row 就相当于某一个具体对象
 * shrds -> //默认分区数
 * replicas -> 每个分区默认的备份数
 * refreshInterval -> //刷新间隔
 * indexStoreType -> //索引文件存储类型
 */
//@Document(indexName = "book",type = "novel",shards = 1,replicas = 0,refreshInterval = "-1")
@Data
@NoArgsConstructor(force=true)
@AllArgsConstructor
public class Book {
    @JSONField
    private Integer id;//主键
    private String title;//书籍名称
    private String author;//书籍作者
    private Integer score;//书籍评分
    private Integer wordCount;//字数
    private String classify; //分类
    private String press;//出版社
    private String content;//内容介绍
    private Date publishDate;//发行时间

}
