package com.ding.common.exception;

/**
 * @Auther: ding
 * @Date: 2019-12-23 12:20
 * @Description:
 */
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
