package com.ding.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.nio.ByteBuffer;

@Slf4j
//@Component
//@ServerEndpoint("/websocket/file")
public class FileWebsocket {
    private FileOutputStream outputStream;
    private FileInputStream inputStream;

    @OnOpen
    public void onOpen(Session session){
        log.info("连接信息...");
    }
    @OnError
    public void onError(Session session, Throwable error){

    }
    @OnMessage
    public void onMessage(Session session,ByteBuffer message){

    }

    @OnMessage
    public void onMessage(Session session,String message,ByteBuffer fileByte){
        log.info("文件信息：" + message + "客户端的id是：" + session.getId());
        try {
            outputStream=new FileOutputStream(new File("/Users/ding/my.gif"));
            outputStream.write(fileByte.array());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnClose
    public void onClose(){

    }
}
