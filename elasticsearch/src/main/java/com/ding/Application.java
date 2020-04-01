/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Application
 * Author:   THINK
 * Date:     2019/3/21 16:44
 * Description: Elasticsearch
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Elasticsearch〉
 *
 * @author THINK
 * @create 2019/3/21
 * @since 1.0.0
 */
@SpringBootApplication
@ComponentScan({"com.ding.*"})
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
//        ApplicationContext applicationContext =
//                SpringApplication.run(Application.class,args);
//        for (String name : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
    }
}
