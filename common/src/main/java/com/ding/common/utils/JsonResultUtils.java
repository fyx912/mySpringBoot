package com.ding.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @description: JSON模板工具类
 * @author: tinTin
 * @create: 2019-04-24 17:47
 */
public class JsonResultUtils {

    public static String message(int code,String msg){
        Map map = new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        return JSONObject.toJSONString(map);
    }

    /**
     *
     * @Description:  封装JSON
     * @auther: tinTin
     * @date:
     * @param code 编码
     * @param object 数据
     * @return:
     */
    public static String success(int code,Object object){
        Map result = new HashMap();
        result.put("code",code);
        result.put("msg","success");
        result.put("date",object);
        return JSONObject.toJSONString(result);
    }
    /**
     * 成功JSON信息
     * @param object
     * @return
     */
    public static String success(Object object){
        JSONObject json = JSON.parseObject(success());
        json.put("date",object);
        return json.toJSONString();
    }
    /**
     * 成功JSON信息
     * @return
     */
    public static String success(){
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","success");
        return JSONObject.toJSONString(map);
    }
    /**
     *
     * @Description: 请求失败类型JSON数据格式
     * @auther:  tinTin
     * @date:
     * @param code 1为通用错误代码
     * @return: 返回json
     */
    public static String failed(){
        Map map = new HashMap();
        map.put("code",1);
        map.put("msg","failed");
        return JSONObject.toJSONString(map);
    }
    /**
     *
     * @Description: 请求失败类型JSON数据格式
     * @auther:  tinTin
     * @date:
     * @param code 1为通用错误代码
     * @return: 返回json
     */
    public static String failed(Object obj){
        JSONObject json = JSON.parseObject(failed());
        json.put("because",obj);
        return json.toJSONString();
    }
    /**
     *
     * @Description: 请求失败JSON数据格式
     * @auther:  tinTin
     * @date:
     * @param code 失败代码
     * @param msg 请求失败原因
     * @return: 返回json
     */
    public static String failed(int code,Object msg){
        Map map = new HashMap();
        map.put("code",code);
        map.put("msg","failed");
        map.put("because",msg);
        return JSONObject.toJSONString(map);
    }

    /**
     *
     * @Description: 错误类型JSON数据格式
     * @auther:  tinTin
     * @date:
     * @param code -1为通用错误代码
     * @param msg 错误信息
     * @return: 返回json
     */
    public static String error(Object msg){
        Map map = new HashMap();
        map.put("code",-1);
        map.put("msg","exception");
        map.put("because",msg);
        return JSONObject.toJSONString(map);
    }

}
