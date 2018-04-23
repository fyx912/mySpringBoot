package com.ding.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @ApiOperation("test")
    @RequestMapping("/")
    public String test(){
        return "hello Swagger";
    }
}
