package com.ding.model;

import com.ding.common.ChatConstants;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class UserInfo {
    public static Map<String, UserInfo> map = new ConcurrentHashMap<>();

    private Long id;

    private String phone;

    private String password;

    private String code;

    private String headImg;

    public UserInfo() {

    }

    public UserInfo(String phone) {
        this.phone = phone;
        this.headImg = ChatConstants.headImg();
        this.code = ChatConstants.code();
        this.id = System.currentTimeMillis();
    }
}
