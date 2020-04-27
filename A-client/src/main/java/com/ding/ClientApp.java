package com.ding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName ServerApp
 * @date 2020-04-18 18:27
 */
@SpringBootApplication
@ComponentScan("com.ding.*")
@MapperScan("com.ding")
public class ClientApp {
    public static void main(String[] args){
        SpringApplication.run(ClientApp.class,args);
    }
}
