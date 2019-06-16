package com.ding.common.exception;

/**
 * @program: mySpringBoot
 * @description: 自定义异常
 * @author: tinTin
 * @create: 2019-04-24 17:54
 */
public class UserException extends RuntimeException {

    private String msg;

    /**
     *  自定义异常
     * @param msg 异常信息
     */
    public UserException(String msg) {
       this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "UserException{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
