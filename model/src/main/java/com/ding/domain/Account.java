package com.ding.domain;

import java.util.Date;

/**
 * @Auther: ding
 * @Date: 2019-09-17 17:42
 * @Description:
 */
public class Account {

    public Integer id;
    public String username;

    public String name;

    public String password;

    public Integer age;

    public String phone;
    public Date date;
    public String address;

    public Account(Integer id,String name,Integer age,String address){
        this.id=id;
        this.name=name;
        this.age=age;
        this.address=address;
    }

    public Account(String username, String name, String password, Integer age, String phone, Date date, String address) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.age = age;
        this.phone = phone;
        this.date = date;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
