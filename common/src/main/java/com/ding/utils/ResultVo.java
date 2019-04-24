package com.ding.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @program: mySpringBoot
 * @description: 根据返回数据对象可封装相应的结果模板工具类
 * @author: tinTin
 * @create: 2019-04-24 17:47
 */
public class ResultVo {

    public static String getOK(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return JSONObject.toJSONString(result);
    }

    public static String getOK(){
        return getOK(null);
    }

    public static String getError(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return JSONObject.toJSONString(result);
    }

}
