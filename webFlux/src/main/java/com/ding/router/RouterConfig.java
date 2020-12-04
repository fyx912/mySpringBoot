package com.ding.router;

import com.ding.web.TimeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName RouterConfig
 * @date 2020-12-03 18:42
 */
@Component
public class RouterConfig {
    @Resource
    private TimeHandler timeHandler;

    @Bean
    public RouterFunction<ServerResponse> myRouter(){
        return route(GET("/time"),request -> timeHandler.getTime(request))
                .andRoute(GET("date"),request -> timeHandler.getDate(request));
    }
}
