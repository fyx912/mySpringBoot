package com.ding.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @Auther: ding
 * @Date: 2019-12-23 12:20
 * @Description:
 */
@ControllerAdvice
public class NullException  extends NullPointerException{
    private String msg;

    public  NullException(){};

    public  NullException(String msg){
        this.msg=msg;
    };

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "NullException{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
