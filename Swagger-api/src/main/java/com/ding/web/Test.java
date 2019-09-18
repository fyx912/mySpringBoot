package com.ding.web;

import com.ding.common.api.ApiVersion;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{version}")
public class Test {

    @ApiVersion(1)
    @ApiOperation("test")
    @RequestMapping("/")
    public String testV1(){
        return "This version v1";
    }

    @ApiVersion(2)
    @RequestMapping("/")
    public String testV2(){
        return "This version v2";
    }

    @ApiVersion(3)
    @RequestMapping("/")
    public String testV3(){
        return "This version v3 ";
    }

    @ApiVersion(4)
    @RequestMapping("/")
    public String testV4(){
        return "This version v3  最高版本";
    }

}
