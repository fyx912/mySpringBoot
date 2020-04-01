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
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈员工实体类〉
 *
 * @author THINK
 * @create 2019/3/21
 * @since 1.0.0
 */

//@Document(indexName = "company",type = "employer",shards = 1,replicas = 0,refreshInterval = "-1")
@Data
@NoArgsConstructor
public class Employer {
    private String id;
    private String firstName;
    private String lastName;
    private Integer age = 0;
    private String about;
    private Date date;

    public Employer(String id) {
        this.id = id;
    }

    public Employer(String id, String firstName, String lastName, Integer age, String about, Date date) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.about = about;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", about='" + about + '\'' +
                ", date=" + date +
                '}';
    }
}
