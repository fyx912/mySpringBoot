package com.ding.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@Slf4j
public class UploadFileHandler extends BinaryWebSocketHandler implements WebSocketHandler{
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(" 连接成功............");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        try{
            FileInputStream inputStream = new FileInputStream("/Users/ding/Downloads/test.jpg");
            //获取指定文件的长度并用它来创建一个可以存放内容的字节数组
            byte[] content = new byte[inputStream.available()];
            inputStream.read(content);
            ByteBuffer byteBuffer = ByteBuffer.wrap(content);
            BinaryMessage binaryMessage = new BinaryMessage(byteBuffer);
            session.sendMessage(binaryMessage);
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
