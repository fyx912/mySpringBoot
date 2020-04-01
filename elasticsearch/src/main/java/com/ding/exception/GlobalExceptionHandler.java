package com.ding.exception;

import com.ding.utils.JsonResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.ServletException;
import java.util.List;


/**
 * @author tintin
 * @version V1.0
 * @describe 全局异常处理
 * @date 2020-03-06 15:57
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handle(Exception e) {

        log.error("exception because:{}", e.getCause());
        e.printStackTrace();

        if (e instanceof IllegalArgumentException) {
            IllegalArgumentException argumentException = (IllegalArgumentException) e;
            return JsonResultUtils.failed(argumentException.getMessage());
        }
        if (e instanceof NullPointerException) {
            return JsonResultUtils.failed("数据不能为空");
        } else if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            log.error("ServiceException info:[{}]", serviceException);
            log.error("ServiceException because:[{}]", e);
            String msg = serviceException.getMsg();
            return JsonResultUtils.failed(msg);
        } else if (e instanceof BindException) {
            BindException be = (BindException) e;
            FieldError error = be.getBindingResult().getFieldError();
            log.error("BindException info:[{}]", be.getMessage());
            String msg = "参数异常请检查";
            if (!msg.isEmpty()) {
                msg = error.getDefaultMessage();
            }
            return JsonResultUtils.failed(2, msg);
        }
        if (e instanceof MultipartException || e instanceof ServletException) {
            return JsonResultUtils.failed("参数或路径有误!");
        } else {
            log.error("【系统异常】,{}", e);
            return JsonResultUtils.error("未知错误！");
        }

    }

    /**
     * 捕捉校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String errorMsg = allErrors.get(0).getDefaultMessage();
        return JsonResultUtils.failed(errorMsg);
    }

}
