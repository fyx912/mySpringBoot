package com.ding.common.exception;

/**
 * @author tintin
 * @version V1.0
 * @Description 自定义服务异常
 * @@copyright <p>富海阳光(北京)技术发展有限公司</p>
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

    public ServiceException(String msg, Throwable cause){
        super(msg,cause);
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

    @Override
    public String toString() {
        return "ServiceException{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
