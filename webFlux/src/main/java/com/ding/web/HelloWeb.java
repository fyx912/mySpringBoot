package com.ding.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName HelloWeb
 * @date 2020-12-03 18:22
 */
@RestController
public class HelloWeb {
    @GetMapping("hello")
    public Mono<String> hello(){
        return Mono.just("Welcome to reactive world ~");
    }
}
