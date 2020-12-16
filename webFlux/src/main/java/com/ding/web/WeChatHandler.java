package com.ding.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName WeChatHandler
 * @date 2020-12-04 17:49
 */
@RestController
@RequestMapping("wechat")
public class WeChatHandler {

    /**
     *  绑定微信公众号
     * @param request
     * @return
     */
//    public Mono<ServerResponse> weChatOfficialAccountBind(ServerRequest serverRequest){
//        Mono<JSONObject> body = serverRequest.bodyToMono(JSONObject.class);
//        System.out.println(body);
//        return ok().contentType(MediaType.TEXT_PLAIN).body(
//                Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date()))
//                ,String.class);
//    }
    @PostMapping("officialAccount/bind")
    public Mono<String> weChatOfficialAccountBind(@RequestBody String data){
        System.out.println(data);
        return  Mono.just("ok");
    }
}
