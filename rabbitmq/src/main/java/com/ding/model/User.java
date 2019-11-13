package com.ding.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class User {
    private String name;
    private String password;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
