package com.ding.Utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CodeJson {

    public static String success(){
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","success");
        return  JSONObject.toJSONString(map);
    }

    public static String success(Object obj){
        Map<String,Object> map = new HashMap();
        map.put("code",0);
        map.put("msg","success");
        map.put("result",obj);
        return  JSONObject.toJSONString(map);
    }

    public static String failed(){
        Map map = new HashMap();
        map.put("code",1);
        map.put("msg","failed");
        return  JSONObject.toJSONString(map);
    }

    public static String failed(Object obj){
        Map map = new HashMap();
        map.put("code",1);
        map.put("msg","failed");
        map.put("failed",obj);
        return  JSONObject.toJSONString(map);
    }

    public static String error(){
        Map map = new HashMap();
        map.put("code",2);
        map.put("msg","error");
        return  JSONObject.toJSONString(map);
    }
}
