package com.ding.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: common
 * @description: 根据返回数据对象可封装相应的结果模板工具类
 * @author: tinTin
 * @create: 2019-04-24 17:47
 */
public class JsonResultUtils {

    public static String message(int code, String msg){
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
     * @param obj 数据
     * @return:
     */
    public static String success(int code, Object obj){
        Map map = new HashMap();
        map.put("code",code);
        map.put("msg","成功");
        map.put("data",obj);
        return jsonFormat(map);
    }

    /**
     * data为null 返回[]
     * 成功JSON信息
     * @param obj
     * @return
     */
    public static String success(Object obj){
        JSONObject json = JSON.parseObject(success());
        json.put("data",obj);
        return jsonFormat(json);
    }

    /**
     * 成功JSON信息,分页
     * @param indexPage 当前第几页
     * @param pageSize 页面大小
     * @param total 总条数
     * @param object
     * @return
     */
    public static String success(Integer indexPage, Integer pageSize, Integer total, Object object){
        JSONObject json = JSON.parseObject(success());
        json.put("data",object);
        json.put("page",dataPage(indexPage,pageSize,total));
        return jsonFormat(json);
    }

    /**
     * 成功JSON信息
     * @return
     */
    public static String success(){
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","成功");
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
        map.put("msg","失败");
        return JSON.toJSONString(map);
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
        json.put("msg",obj);
        return JSON.toJSONString(json);
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
    public static String failed(int code, Object obj){
        Map map = new HashMap();
        map.put("code",code);
        map.put("msg",obj);
        return JSON.toJSONString(map);
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
    public static String error(Object obj){
        Map map = new HashMap();
        map.put("code",-1);
        map.put("msg",obj);
        return JSON.toJSONString(map);
    }


    /**
     *  数据分页
     * @param indexPage 第几页
     * @param pageSize 当前页共多少页
     * @param total 总共多少条
     * @return
     */
    public static Map<String, Integer> dataPage(Integer indexPage, Integer pageSize, Integer total){
        Map map = new HashMap();
        map.put("pageDown",false);//下页
        map.put("pageUp",false);//上页
        map.put("startPage",1);//初始页
        if (isEmpty(indexPage) || 0 >= indexPage )
            indexPage = 1;
        map.put("indexPage",indexPage);//索引页
        if (isEmpty(pageSize)|| 0 >= pageSize || pageSize >= 100)
            pageSize = 10;
        map.put("pageSize",pageSize);//页面大小
        int indexPageSize = (indexPage -1)*pageSize +1;
        map.put("indexPageSize",indexPageSize);//索引页面条数
        int endPageSize = indexPage * pageSize;
        if (endPageSize > total)
            endPageSize = total;
        map.put("endPageSize",endPageSize);//索引结束页面条数

        int  endPage =  (total % pageSize == 0 ) ? ( total / pageSize ) :(total / pageSize + 1) ;//共多少页面
        map.put("endPage",endPage);
        map.put("total",total);//总条数目
        if ( indexPage < endPage)
            map.put("pageDown",true);//下页
        if (1 < indexPage && indexPage <= endPage)
            map.put("pageUp",true);//上页
        return map;
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 将不同类的参数null转换为[]或""
     * @param obj
     * @return
     */
    public static String jsonFormat(Object obj){
        return JSON.toJSONString(obj, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue);
    }

}
