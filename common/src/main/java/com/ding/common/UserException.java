package com.ding.common;

/**
 * @program: mySpringBoot
 * @description: 自定义异常
 * @author: tinTin
 * @create: 2019-04-24 17:54
 */
public class UserException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UserException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
