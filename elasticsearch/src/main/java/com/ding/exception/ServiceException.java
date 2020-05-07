package com.ding.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName ServiceException
 * @date 2020-03-11 16:29
 */
public class ServiceException extends RuntimeException {

    private String msg;
    private Integer code;

    public ServiceException(Integer code) {
        this.code = code;
    }

    public ServiceException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg,Throwable cause){
        super(msg,cause);
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
