package com.ding.common;

import com.ding.utils.Result;
import com.ding.utils.ResultVo;
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
        if (e instanceof UserException){
            UserException userException = (UserException)e;
            log.error("UserException code:[{}],msg:[]",((UserException) e).getCode(),e.getMessage());
            return ResultVo.getError(userException.getCode(),userException.getMessage());
        }else{
            log.error("【系统异常】={}",e);
            return ResultVo.getError(-1,"未知错误！");
        }

    }
}
