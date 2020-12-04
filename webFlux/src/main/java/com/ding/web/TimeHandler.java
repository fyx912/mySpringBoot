package com.ding.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName TimeHandle
 * @date 2020-12-03 18:43
 */
@Component
public class TimeHandler {

    public Mono<ServerResponse> getTime(ServerRequest request){
        return ok().contentType(MediaType.TEXT_PLAIN).body(
                Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date()))
                ,String.class);
    }

    public Mono<ServerResponse> getDate(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Today is " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())), String.class);
    }
}
