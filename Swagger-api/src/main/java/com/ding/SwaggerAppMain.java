package com.ding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableAutoConfiguration
@EnableCaching
@SpringBootApplication
public class SwaggerAppMain {
    public static void main(String[] args){
        SpringApplication.run(SwaggerAppMain.class,args);
    }
}
