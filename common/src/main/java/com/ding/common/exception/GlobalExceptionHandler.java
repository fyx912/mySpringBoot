package com.ding.common.exception;

import com.ding.common.utils.JsonResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: mySpringBoot
 * @description: 全局异常处理
 * @author: tinTin
 * @create: 2019-04-24 17:49
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handle(Exception e) throws Exception{
        if (e instanceof UserException ){
            UserException my = (UserException) e;
            log.error("UserException info:{}",my.getMsg());
//            log.error("UserException:",e);
            return JsonResultUtils.failed(my.getMsg());
        }else if (e instanceof NullPointerException || e instanceof NullException){
            if (e instanceof NullPointerException){
                log.error("UserException info:{}","数据不能为空");
            }
            return JsonResultUtils.failed("数据不能为空");
        }else{
            log.error("【系统异常】,{}",e);
            e.printStackTrace();
            return JsonResultUtils.failed("未知错误!");
        }

    }
}
