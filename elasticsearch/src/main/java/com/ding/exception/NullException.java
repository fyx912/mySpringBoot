package com.ding.exception;

/**
 * @author tintin
 * @version V1.0
 * @describe null异常
 * @date 2020-03-06 16:03
 */
public class NullException extends NullPointerException {
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
