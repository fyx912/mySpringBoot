package com.ding.utils;

import java.io.IOException;
import java.util.List;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName EsInterface
 * @date 2020-03-25 19:28
 */
public interface EsUtilService {

    public boolean checkIndexExist(String index);
    public boolean create(String index);
    public boolean update(String index);
    public boolean delete(String index) throws IOException;

    public long count(String index);
    /**
     *
     * @param index 数据库索引
     * @param fieldKey  需要查询的字段
     * @param value     需要查询的值
     * @param startRow   开始
     * @param size      当前页面条数
     * @return
     * @throws IOException
     */
    public List select(String index,String fieldKey,Object value,Integer startRow,Integer size);

}
