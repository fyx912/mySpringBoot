package com.ding.elk.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName TestWeb
 * @date 2020-12-14 16:48
 */
@RestController
public class TestWeb {
    private Logger log = LoggerFactory.getLogger(TestWeb.class);

    @GetMapping("test")
    public String test(){
        log.info("hello elk!");
        log.warn("This is Warn message!");
        log.error("This is error message!");
        return "server被调用了！";
    }
}
