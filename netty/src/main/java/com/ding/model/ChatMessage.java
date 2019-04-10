package com.ding.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ding.common.ChatConstants;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ChatMessage {
    //发送消息则
    private UserInfo from;

    //发送内容
    private String message;

    //接收者列表
    private Map<String, UserInfo> to;

    //发送时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public ChatMessage() {

    }

    public ChatMessage(UserInfo from,String message) {
        this.from = from;
        this.message = message;
        this.to = ChatConstants.onlines;
        this.createTime = new Date();
    }
}
