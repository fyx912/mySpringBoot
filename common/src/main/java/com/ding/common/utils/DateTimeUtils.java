package com.ding.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * 日期处理工具类
 * @auther: tinTin
 * @date:
 */
public class DateTimeUtils {
    /**
     *
     * @Description: yyyy-MM-dd hh:mm:ss格式
     * @auther: tinTin
     * @date:
     * @param:
     * @return:
     */
    public String dateFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  dateFormat.format(date);
    }
}
