package com.ding.web;

import com.ding.common.utils.CodeMessage;
import com.ding.common.utils.ProfileResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ding
 * @Date: 2020-01-02 16:03
 * @Description:
 */
@RestController
@RequestMapping("code")
public class CodeWeb {

    @GetMapping("")
    public String code(){
        return CodeMessage.json(1003);
    }


    @Autowired
    private ProfileResolver profileResolver;

    /**
     * Spring 方式解析配置文件
     * @return
     */
    @GetMapping("code")
    public String codeSpring(){
        System.out.println(profileResolver.code("0"));
        System.out.println(profileResolver.code("1"));
        System.out.println(profileResolver.code("1003"));

        return profileResolver.failedJson(1003);

    }
}
