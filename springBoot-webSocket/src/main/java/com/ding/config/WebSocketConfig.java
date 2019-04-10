package com.ding.config;

import com.ding.handler.DownloadsFileWebSocketHandler;
import com.ding.handler.UploadFileHandler;
import com.ding.interceptor.HandlerShakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


@EnableWebSocket  //声明支持websocket
@Configuration
public class WebSocketConfig  implements WebSocketConfigurer {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //addHandler注册和路由的功能，当客户端发起websocket连接，把/path交给对应的handler处理，而不实现具体的业务逻辑，可以理解为收集和任务分发中心。
        //setAllowedOrigins(String[] domains),允许指定的域名或IP(含端口号)建立长连接，默认只有本地。如果不限时使用"*"号，如果指定了域名，则必须要以http或https开头。
        //addInterceptors，顾名思义就是为handler添加拦截器，可以在调用handler前后加入自定义的逻辑代码。
        registry.addHandler(downloadsFileWebSocketHandler(),"/websocket/downloadsFile").addInterceptors(handshakeInterceptor()).setAllowedOrigins("*");
        //允许客户端使用SockJS
        //SockJS 是一个浏览器上运行的 JavaScript 库，如果浏览器不支持 WebSocket，该库可以模拟对 WebSocket 的支持。
        registry.addHandler(downloadsFileWebSocketHandler(), "/sockjs/socketServer.do").addInterceptors(handshakeInterceptor()).withSockJS();

        registry.addHandler(binaryWebSocketHandler(),"/websocket/uploadFile").addInterceptors(handshakeInterceptor()).setAllowedOrigins("*");
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new HandlerShakeInterceptor();
    }


    @Bean
    public BinaryWebSocketHandler downloadsFileWebSocketHandler(){
        return new DownloadsFileWebSocketHandler();
    }

    @Bean
    public BinaryWebSocketHandler binaryWebSocketHandler(){
        return  new UploadFileHandler();
    }
}
