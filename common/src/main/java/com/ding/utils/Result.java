package com.ding.utils;

/**
 * @program: mySpringBoot
 * @description: 统一返回对象实体
 * @author: tinTin
 * @create: 2019-04-24 17:40
 */
public class Result<T> {

    private Integer code;

    private Integer status;

    private String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
