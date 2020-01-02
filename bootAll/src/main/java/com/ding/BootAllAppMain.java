package com.ding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


//@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
public class BootAllAppMain {
    public static void main(String[] args){
        SpringApplication.run(BootAllAppMain.class,args);
    }
}
