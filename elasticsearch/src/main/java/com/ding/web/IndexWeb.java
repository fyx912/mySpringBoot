package com.ding.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName IndexWeb
 * @date 2020-04-01 15:26
 */
@RestController
public class IndexWeb {

    @GetMapping("")
    public String index(){
        return "welcome use elasticsearch 7.x services!";
    }
}
