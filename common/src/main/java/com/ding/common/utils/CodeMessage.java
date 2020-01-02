package com.ding.common.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 解析code.properties
 * @Auther: ding
 * @Date: 2020-01-02 15:42
 * @Description:
 */
public class CodeMessage {
    private static Logger  log = LoggerFactory.getLogger(CodeMessage.class);
    public static String httpCode= "/code.properties";

    public static String getMessageStatusCode(int key){
        String value="";
        String path =CodeMessage.class.getClassLoader().getResource("config").getPath() + httpCode;
        try {
            Properties properties = new Properties();
            InputStream in = new FileInputStream(path);
            BufferedReader bis = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            properties.load(bis);
            value = properties.getProperty(String.valueOf(key));
        }catch (IOException e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return value;
    }

    /**
     *  消息状态码封装成json
     *  配置文件状态码信息封装成json
     * @param key 状态码
     * @return json
     */
    public static  String messagePackJson(int key){
        Map map = new HashMap();
        map.put("code",key);
        map.put("msg",getMessageStatusCode(key));
        return JSON.toJSONString(map);
    }

    /**
     * 消息状态码封装成json
     * @param key
     * @return
     */
    public static  String json(int key){
        return  messagePackJson(key);
    }

    public static void main(String[] args){
        System.out.println(CodeMessage.class.getClassLoader().getResource("config").getPath());
        System.out.println(getMessageStatusCode(1002));
        System.out.println(messagePackJson(1002));
    }
}
