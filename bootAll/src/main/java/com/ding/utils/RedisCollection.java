package com.ding.utils;

import java.util.*;

/**
 * @author tintin
 * @version V1.0
 * @Description redis 数据集
 * @@copyright
 * @ClassName RedisCollection
 * @date 2020-03-12 19:53
 */
public abstract  class  RedisCollection<T> {
    /**
     * redis 数据类型
     * string（字符串) ,hash（哈希）,list（列表）,set（集合）及zset(sorted set：有序集合)
     */
    public String[] dataStructure = {"string","hash","list","set","zset"};

    /**
     *
     * 保存集合数据
     * @param type  redis 数据类型:
     * @param key redis key
     * @param values 数据集
     * @return true or false
     */
    public abstract boolean saveData(String key, String type, Object values);

    /**
     * 获取获取数据集
     * @param key redis key
     * @param index 索引 redis索引从0开始
     * @param type  redis 数据类型
     * @return
     */
    public abstract Object getData(String key, String type, Integer index);


    /**
     * redis 数据类型校验
     * @param type redis 数据类型不区分大小写
     * @return
     */
    public int stringDataCheck(Object value){
        Integer size = dataStructure.length;
        if (size>0){
            for (int i = 0; i < size ; i++) {
                if (value instanceof String) {
                    return i;
                }
                if ((value instanceof Map|| value instanceof HashMap) ) {
                    return i;
                }
                if ((value instanceof List || value instanceof ArrayList || value instanceof LinkedList) ) {
                    return i;
                }
                if ((value instanceof Set || value instanceof ArrayList || value instanceof LinkedList) ) {
                    return i;
                }else {
                    return 4; //zset
                }
            }
        }
        return  0;
    }

    /**
     * redis 数据类型顺序
     * @param type redis 数据类型不区分大小写
     * @return
     */
    public int dataStructureOrder(String type){
        Integer size = dataStructure.length;
        if (size>0){
            for (int i = 0; i < size ; i++) {
                if (dataStructure[i].equalsIgnoreCase(type)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
