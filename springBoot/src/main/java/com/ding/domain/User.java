package com.ding.domain;

import java.io.Serializable;

public class User implements Serializable {
    public Integer id;
    public String userName;
    public String name;
    public String password;
    public Integer age;
    public String phone;

    public User() {
    }

    public User(Integer id, String userName, String name, String password, Integer age, String phone) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.age = age;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}