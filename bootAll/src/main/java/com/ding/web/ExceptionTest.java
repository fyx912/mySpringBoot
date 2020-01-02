package com.ding.web;

import com.ding.common.exception.NullException;
import com.ding.common.exception.UserException;
import com.ding.common.utils.ProfileResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: mySpringBoot
 * @description:
 * @author: tinTin
 * @create: 2019-04-24 17:56
 */
@RestController
public class ExceptionTest {

    /**
     * 自定义异常
     * @param name
     * @return
     */
    @GetMapping("exception")
    public String test(String name){
        if (name==null){
            throw new UserException("request param 'name' not is null!");
        }
        return "test";
    }

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @GetMapping("exception-all")
    public Map<String,Object> exceptionAll(String name){
        if (name.equals(null)) {
            throw   new NullException();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg",name);
        return map;

    }

}
