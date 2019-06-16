package com.ding.common.exception;

import com.ding.common.utils.JsonResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: mySpringBoot
 * @description: 异常处理
 * @author: tinTin
 * @create: 2019-04-24 17:49
 */

@ControllerAdvice
public class ExceptionHandle {
    private Logger log = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handle(Exception e){
        if (e instanceof UserException ){
            UserException my = (UserException) e;
            log.error("UserException info:{}",my.getMsg());
//            log.error("UserException:",e);
            return JsonResultUtils.failed(my.getMsg());
        }else{
            log.error("【系统异常】,{}",e);
            e.printStackTrace();
            return JsonResultUtils.error("未知错误！");
        }

    }
}
