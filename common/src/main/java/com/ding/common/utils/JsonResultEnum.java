package com.ding.common.utils;

public enum JsonResultEnum {
    SUCCESS(true,0,"成功"),
    FAILED(false,1,"失败"),
    NOT_NULL(false,2,"不能用空"),
    WRONG_PARAMETER(false,3,"参数有误")
    ;

    //响应成功
    private Boolean success;
    //响应状态码
    private Integer code;
    //响应信息
    private String message;

    JsonResultEnum(boolean success, Integer code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
