package com.ding.web;

import com.ding.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName IndexWeb
 * @date 2020-03-09 12:18
 */
@RestController
public class IndexWeb {

    @GetMapping("/")
    public String index(){
        return  "welcome index";
    }


    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/test")
    public String indexTest(){
       boolean flag = redisUtils.set("ding","123455");
       System.out.println(flag);
        return redisUtils.get("ding").toString() ;
    }

    @GetMapping("/exception")
    public String exception(){
        throw  new NullPointerException("异常信息");
    }
}
