package com.ding.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 *
 * 这是一个初始化器，chanel注册后会执行里面相应的初始化方法(也就是将handler逐个进行添加)
 */
public class ChatNettyServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;

    public ChatNettyServerInitializer(ChannelGroup group){
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //处理日志
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

        //处理心跳
        pipeline.addLast(new IdleStateHandler(0,0,1800, TimeUnit.SECONDS));


        // 将请求与应答消息编码或者解码为HTTP消息
        pipeline.addLast("http-codec", new HttpServerCodec());
        // 向客户端发送HTML5文件。主要用于支持浏览器和服务端进行WebSocket通信
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast(new ChunkedWriteHandler());
        // 将http消息的多个部分组合成一条完整的HTTP消息
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));

    }
}
