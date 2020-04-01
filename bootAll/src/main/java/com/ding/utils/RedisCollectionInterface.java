package com.ding.utils;

/**
 * @author tintin
 * @version V1.0
 * @Description redis 数据集处理接口
 * @@copyright
 * @ClassName RedisCollectionInterface
 * @date 2020-03-12 19:29
 */
public interface RedisCollectionInterface {

    /**
     * 保存集合数据
     * @param key redis key
     * @param values 数据集
     * @return true or false
     */
    public boolean saveDatas(String key, Object values);

    /**
     * 获取获取数据集
     * @param key redis key
     * @param index 索引 redis索引从0开始
     * @return
     */
    public String getDatas(String key, Integer index);
}
