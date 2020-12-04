package com.ding.domain;

import java.io.Serializable;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName User
 * @date 2020-11-10 13:44
 */
public class User implements Serializable {
    private Integer id;
    private String userName;
    private String password;
    private String name;
    private String phone;
    private Integer age;
    /**
     * 1男
     * 2女
     * 3保密
     */
    private Integer gender;

    public User() {
    }

    public User(Integer id, String userName, String password, Integer age, Integer gender) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
