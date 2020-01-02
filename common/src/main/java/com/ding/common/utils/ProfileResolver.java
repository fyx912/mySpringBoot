package com.ding.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  spring boot 注解方式解析配置文件
 * @Auther: ding
 * @Date: 2020-01-02 10:56
 * @Description:
 */
@Component
@PropertySource(value = "config/code.properties",encoding = "UTF-8")
public class ProfileResolver {
    @Autowired
    private  Environment env;

    public void setEnv(Environment env) {
        this.env = env;
    }

    /**
     * 根据输入的code key获取msg的value
     * @param key
     * @return
     */
    public String code(Object key){
        if ( key == null ||key.equals("")){
            throw new NullPointerException();
        }
        return  env.getProperty(key.toString());
    }


    /**
     * 根据输入的code key获取msg的value
     * @param key
     * @return
     */
    public String failedJson(Object key){
        if ( key == null ||key.equals("")){
            throw new NullPointerException();
        }
        Map map = new HashMap();
        map.put("code",key);
        map.put("msg",env.getProperty(key.toString()));
        return JSONObject.toJSONString(map);
    }

}
