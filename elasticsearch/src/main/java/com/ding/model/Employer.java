/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Employee
 * Author:   THINK
 * Date:     2019/3/21 16:46
 * Description: 员工实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ding.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * 〈一句话功能简述〉<br> 
 * 〈员工实体类〉
 *
 * @author THINK
 * @create 2019/3/21
 * @since 1.0.0
 */

@Document(indexName = "company",type = "employer",shards = 1,replicas = 0,refreshInterval = "-1")
@Data
public class Employer {
    @Id
    private String id;
    @Field
    private String firstName;
    @Field
    private String lastName;
    @Field
    private Integer age = 0;
    @Field
    private String about;
}
