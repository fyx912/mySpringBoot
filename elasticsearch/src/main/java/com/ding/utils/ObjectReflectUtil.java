package com.ding.utils;

import com.alibaba.fastjson.JSON;
import com.ding.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tintin
 * @version V1.0
 * @Description 对象反射操作
 * @@copyright
 * @ClassName ObjectReflectUtil
 * @date 2020-03-25 18:36
 */
public class ObjectReflectUtil {
    private static Logger logger = LoggerFactory.getLogger(ObjectReflectUtil.class);
    /**
     *  获取对象或key和type，put到map
     * @param obj 对象
     * @return
     */
    public static Map<String,Object> getObjectKeyAndType(Object obj){
        Field[] fields= obj.getClass().getDeclaredFields();
        Map<String,Object>  map = new HashMap<>();
        for (Field field :fields){
            field.setAccessible(true);
            map.put(field.getName(),field.getType());
        }
        return map;
    }

    /**
     *  获取对象或key和value，put到map
     * @param obj 对象
     * @return
     */
    public static Map<String,Object> getObjectKeyAndValue(Object obj){
        Field[] fields= obj.getClass().getDeclaredFields();
        Map<String,Object>  map = new HashMap<>();
        try {
            for (Field field :fields){
                field.setAccessible(true);
//                if ()
                map.put(field.getName(),field.get(obj));
                System.out.println(field.getName()+"\t"+field.get(obj));
            }
        }catch (IllegalAccessException e) {
            logger.error("Object to map exception! because:{} ",e);
        }
        List list = (List) map.get("entrySet");
        Map<String,Object>  objectMap = new HashMap<>();
        for (Map.Entry<String,Object> entry: map.entrySet()){
            objectMap.put(entry.getKey(),entry.getValue());
        }
        map.clear();
        logger.info("object to map ,json:{}", JSON.toJSONString(objectMap));
        return objectMap;
    }

    /**
     *  根据key获取对象value
     * @param obj 对象
     * @param key key
     * @return
     */
    public static String getObjectKeyAndValue(Object obj ,String key){
       String result = null;
       try {
           Field[] fields= obj.getClass().getDeclaredFields();
           for (Field field :fields){
               field.setAccessible(true);
               if (key.equals(field.getName())){
                   result = field.get(obj).toString();
               }
           }
       } catch (IllegalAccessException e){
           throw  new IllegalArgumentException("Object parsing exception",e);
       }
       return result;
    }

    public static void main(String[] args){
        System.out.println(getObjectKeyAndValue(new Book(),""));
    }

}
