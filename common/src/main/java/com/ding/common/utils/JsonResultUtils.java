package com.ding.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ding.common.exception.ServiceException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author ding
 * @version V3.0
 * @Classname JsonResult
 * @Description JSON处理工具类
 * @copyright <p>富海阳光(北京)技术发展有限公司</p>
 * @Date 2020/4/20 下午3:41
 */
public class JsonResultUtils {

    //通用返回成功
    public static String success(){
       Map map = new HashMap();
       map.put("code",JsonResultEnum.SUCCESS.getCode());
       map.put("msg",JsonResultEnum.SUCCESS.getMessage());
       return  JSON.toJSONString(map);
    }

    //通用返回成功
    private static JSONObject successMap(){
        JSONObject map = new JSONObject(true);
        map.put("code",JsonResultEnum.SUCCESS.getCode());
        map.put("msg",JsonResultEnum.SUCCESS.getMessage());
        return map;
    }


    //通用返回成功
    public static String success(Integer code, String message){
        Map map = new HashMap();
        map.put("code",code);
        map.put("msg",message);
        return  JSON.toJSONString(map);
    }

    //通用返回成功
    public static String success(Object value){
        JSONObject jsonObject = successMap();
        if (value == null){
            jsonObject.put("data","");
        }else {
            jsonObject.put("data",value);
        }
        return  jsonFormat(jsonObject);
    }


    //通用返回成功
    public static String success(Integer code, String message,Object value){
        Map map = new HashMap();
        map.put("code",code);
        map.put("msg",message);
        if (value == null){
            map.put("data","");
        }else {
            map.put("data",value);
        }
        return  jsonFormat(map);
    }

    //通用返回失败
    public static String failed(){
        Map map = new HashMap();
        map.put("code",JsonResultEnum.FAILED.getCode());
        map.put("msg",JsonResultEnum.FAILED.getMessage());
        return  JSON.toJSONString(map);
    }

    //通用返回失败
    public static String failed(String massages){
        Map map = new HashMap();
        map.put("code",JsonResultEnum.FAILED.getCode());
        map.put("msg",massages);
        return  JSON.toJSONString(map);
    }

    //通用返回失败
    public static String failed(Integer code, String massages){
        Map map = new HashMap();
        map.put("code",code);
        map.put("msg",massages);
        return  JSON.toJSONString(map);
    }

    /**
     *  返回结果判断，并输出处理成功或失败的json
     * @param obj result
     * @return
     */
    public static  String resultTOJson(Object obj){
        if (obj instanceof  Integer){
            Integer result = (Integer) obj;
            if (result!=null&&result>0){
                return success();
            }
        }
        if (obj instanceof  Boolean){
            Boolean flag = (Boolean) obj;
            if (flag){
                return success();
            }
        }

        return failed();
    }

    /**
     * 将不同类的参数null转换为[]或""
     * @param obj
     * @return
     */
    public static String jsonFormat(Object obj){
        return JSON.toJSONString(obj,
                SerializerFeature.WriteNonStringKeyAsString,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteBigDecimalAsPlain,
                SerializerFeature.WriteNullNumberAsZero, //数值字段如果为null,输出为0,而非null
                SerializerFeature.WriteNullBooleanAsFalse //Boolean字段如果为null,输出为false,而非null
        );
    }

    /**
     * 校验json格式数据是否成功
     * @return
     */
    public static boolean dataValidation(String  jsonResult){
        if (StringUtils.isEmpty(jsonResult)){
            throw  new NullPointerException("接收json数据为空!");
        }
        JSONObject jsonObject = JSON.parseObject(jsonResult);
        if (jsonObject.getString("code") instanceof  String){
            if (jsonObject.get("code").equals("0")){
                return  true;
            }
        }
        if (jsonObject.getInteger("code") instanceof  Integer){
            if (jsonObject.getIntValue("code")==0){
                return  true;
            }
        }
        return false;
    }

    /**
     *  json 转对象
     * @param json   json
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> T  jsonToEntity(String json,Class<T> clazz){
        dataValidation(json);
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey("data")){
            Optional.of(jsonObject.get("data"));
           return JSON.toJavaObject(jsonObject.getJSONObject("data"),clazz);
        }
        throw new ServiceException(2,"数据异常!");
    }

    /**
     * 对象转JSON
     * @param obj
     * @return
     */
    public static  String ObjectToJson(Object obj){
        return  JSON.toJSON(obj).toString();
    }

//
//    /**
//     * ------------------使用链式编程,返回类本身----------------------------
//     */
//
//    //自定义返回数据
//    public JsonResult data(Map<String, Object> map){
//        this.setData(map);
//        return this;
//    }
//
//    //通用data
//    public JsonResult data(String key, Object value){
//        this.data.put(key,value);
//        return this;
//    }
//
//    public JsonResult message(String message){
//        this.setMsg(message);
//        return this;
//    }
//
//    public JsonResult code(Integer code){
//        this.setCode(code);
//        return this;
//    }



}
