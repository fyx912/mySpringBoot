package com.ding.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class DownloadsFileWebSocketHandler extends BinaryWebSocketHandler implements WebSocketHandler {
    private FileOutputStream outputStream;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private  Map<String, Object> clientMap = new HashMap<>();
    private  Queue<ByteBuffer> queue = new ConcurrentLinkedQueue<>();

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info( " webSocket Connection ......"+session.getRemoteAddress());
        Thread.sleep(2000);
        TextMessage message = new TextMessage("connection is success!");
        session.sendMessage(message);
        log.info("回复message："+message);
    }

    /**
     * 关闭连接时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info(" webSocket closed ......"+session.getRemoteAddress()+" "+session);
    }

    /**
     * js调用webSocket.send时候，会调用该方法
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage message) throws Exception {
//        session.setBinaryMessageSizeLimit(1024*1024*1024*1024);
        log.info(" message ", (message instanceof TextMessage));
        if (message instanceof TextMessage){
            handleTextMessage(session,(TextMessage) message);
        }else if (message instanceof BinaryMessage){
            handleBinaryMessage(session,(BinaryMessage) message);
        }else if (message instanceof PongMessage){
            handlePongMessage(session,(PongMessage) message);
        }else {
            throw new IllegalStateException("Unexpected WebSocket message type:"+message);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
//        log.info("处理请求 。。。。session:{} \n messages:{}"+session,message);
        log.info("收到的 messages : "+message.getPayload());
        String  payload =  message.getPayload();
        if (payload.endsWith(":fileStart")){
            clientMap.put("fileName",payload.split(":")[0]);
            log.info("map save fileName:",clientMap.get("fileName"));
        }
        if (payload.equals("fileSendSuccess")){
            clientMap.put("fileSendSuccess",true);
        }
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        log.info("二进制处理......",message.getPayloadLength());
        ByteBuffer buffer =  message.getPayload();
        System.out.println("length:"+message.getPayloadLength()+",isLast"+message.isLast()+",:"+message.getPayload().array().length);
        log.info("file ===",clientMap.get("fileName"));
        System.out.println(clientMap.get("fileSendSuccess"));
        queue.add(buffer);
        boolean flag = false;
        if (clientMap.get("fileSendSuccess")!=null){
            flag = (boolean)clientMap.get("fileSendSuccess");
        }
        if (flag){
            clientMap.remove("fileSendSuccess");
            try {
                String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
                path = path.substring(0,path.length()-8)+"resources/images/";
                log.info(path);
                System.out.println(" queue size===" +queue.size());
                outputStream=new FileOutputStream(new File("/Users/ding/"+clientMap.get("fileName")));
                while (!queue.isEmpty()){
                    outputStream.write(queue.poll().array());
                }
                System.out.println("queue isEmpty:"+ queue.size());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        uploadBold(buffer);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    /**
     * websocket  一次接受大文件 ，websocket 文件有限制
     * @param buffer
     */
    public void uploadBold(ByteBuffer buffer) {
        try{
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            path = path.substring(0,path.length()-8)+"resources/images/";
            log.info(path);
            outputStream=new FileOutputStream(new File("/Users/ding/"+clientMap.get("fileName")));
            while (!queue.isEmpty()){
                outputStream.write(buffer.array());
            }
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 分片接收
     * @param buffer
     */
    public void uploadBoldTo(ByteBuffer buffer){

    }
}
