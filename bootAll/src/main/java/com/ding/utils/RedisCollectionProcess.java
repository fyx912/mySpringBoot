package com.ding.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tintin
 * @version V1.0
 * @Description 数据集处理
 * @@copyright
 * @ClassName RedisCollectionProcess
 * @date 2020-03-12 19:45
 */
public class RedisCollectionProcess extends RedisCollection{
    private Logger log = LoggerFactory.getLogger(RedisCollectionProcess.class);
    @Autowired
    RedisUtils redisUtils;

    @Override
    public  boolean saveData(String key, String type, Object values) {
        boolean flag = false;
        String json = JSONObject.toJSON(values).toString();
        log.info(" redis collection  data, methodType:[{}],JSON:[{}]",key, json);
        switch (this.stringDataCheck(values)){
            case 0: //string
                flag = redisUtils.set(key,json);
                break;
            case 1: //hash
                flag = redisUtils.hmset(key, JSON.parseObject(json,Map.class)); break;
            case 2: //list
                flag = redisUtils.lSet(key,json); break;
            case 3: //set
                int keySize = 0;
                if (values instanceof HashMap || values instanceof Map){
                    keySize = ((Map) values).size();
                }
                Long saveSize = redisUtils.sSet(key,json);
                if (keySize==saveSize.intValue()){
                    flag =true;
                }else {
                    log.warn(" redis collection  data, save_status=failed ! methodType:[{}],size:[{}],save_size[{}],JSON:[{}]",key, keySize,saveSize,json);
                }
                log.info(" redis collection  data,save_status:[{}]  methodType:[{}],size:[{}],save_size,JSON:[{}]",flag,key, keySize,saveSize,json);
                break;
//            case 4://zset(有序集合)
//                flag = redisUtils.lSet(key,json); break;
           default://string
                flag = redisUtils.set(key,json);
               break;
        }
            return flag;
    }

    @Override
    public Object getData(String key, String type, Integer index) {
        Object obj;
        switch (this.dataStructureOrder(type)){
            case 0: //string
                obj =  redisUtils.get(key);break;
            case 1: //hash
                obj = redisUtils.hmget(key); break;
            case 2: //list
                obj =  redisUtils.lGetIndex(key,index); break;
            case 3: //set
                obj  = redisUtils.sGet(key);
//            case 4://zset(有序集合)
//                flag = redisUtils.lSet(key,json); break;
            default://string
                obj =  redisUtils.get(key);break;
        }
        return obj;
    }

}
