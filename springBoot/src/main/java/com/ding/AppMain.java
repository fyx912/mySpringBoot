package com.ding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;


@EnableCaching
@ServletComponentScan
@EnableAutoConfiguration
@ComponentScan
@MapperScan(basePackages ="com.ding.mapper")
@SpringBootApplication(scanBasePackages = "com.ding")
public class AppMain {
    public static void main(String[] args){
        SpringApplication.run(AppMain.class,args);
    }
}
