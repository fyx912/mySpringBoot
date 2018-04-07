package com.ding.controller;

import com.alibaba.fastjson.JSONObject;
import com.ding.Utils.CodeJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SystemInfo {

    @RequestMapping(value = "os",method = RequestMethod.GET)
    public String systemInfo(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        map.put("os",System.getProperty("os.name"));
        map.put("os.arch",System.getProperty("os.arch"));
        map.put("os.version",System.getProperty("os.version"));
        map.put("jdk",System.getProperty("java.version"));
        map.put("serverName",request.getServerName());
        return CodeJson.success(JSONObject.toJSONString(map));
    }

}
