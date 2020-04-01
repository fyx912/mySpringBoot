package com.ding.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author tintin
 * @version V1.0
 * @Description AOP切面日志处理
 * @ClassName WebAspect
 * @date 2020-03-06 16:15
 */
@Aspect
@Component
public class WebAspect {
    private  final Logger logger = LoggerFactory.getLogger(WebAspect.class);

    //时间同步
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * Pointcut
     * @Description: 定义切点,参数说明:1.execution(方法执行时触发);2.*(返回任意参数类型);3.package下所有的方法
     * @auther: tinTin
     */
    @Pointcut("execution(public * com..*.controller..*.*(..))||execution(public * com..*.web..*.*(..))")
    public void webLog() {
    }

    @Pointcut("execution(public * com.*.*.service..*.*(..))")
    public void serviceLog() {
    }

    @Pointcut("execution(public * com..*.repository..*.*(..))||execution(public * com..*.dao..*.*(..))")
    public void daoLog() {
    }

    /**
     *
     * @param joinPoint 连接点（Joinpoint）：可能被增强的点，目标类中的所有方法
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 记录下请求内容
        logger.info("start Record Time :{} ",startTime.get());
        logger.info("os:{},ip:{},port:{}",System.getProperty("os.name"),request.getRemoteAddr(),request.getServerPort());
        logger.info("URL:{},method:{}",request.getRequestURI(),request.getMethod());
//        logger.info("sessionId:{} ",request.getSession());
        logger.info("class_method: {}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        logger.info("parameter Args: {}",Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "response",pointcut = "webLog()")
    public void doAfterReturning(Object response){
        // 处理完请求，返回内容
        logger.info("response result:{}", response);
        logger.info("end time :{}ms.  \n",(System.currentTimeMillis()-startTime.get()));
    }
}
