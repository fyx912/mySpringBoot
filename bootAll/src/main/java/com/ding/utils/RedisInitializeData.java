package com.ding.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author tintin
 * @version V1.0
 * @Description 数据初始化
 * @@copyright
 * @ClassName InitializeData
 * @date 2020-03-12 19:29
 */
public class RedisInitializeData {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 初始化数据
     * @param key
     * @param list
     */
    public void initListData(String key, List list){
        redisUtils.del(key);
        String dataJson =  JSONObject.toJSON(list).toString();
        redisUtils.lSet(key,dataJson);
    }
}
