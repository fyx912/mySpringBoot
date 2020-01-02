package com.ding.common.monitor;

import com.alibaba.fastjson.JSONObject;
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
 *
 * @Description: AOP
 * 5种通知类型(
 * Before——在方法调用之前调用通知
 * After——在方法完成之后调用通知，无论方法执行成功与否
 * After-returning——在方法执行成功之后调用通知
 * After-throwing——在方法抛出异常后进行通知
 * Around——通知包裹了被通知的方法，在被通知的方法调用之前和调用之后执行自定义的行为
 * ）
 * @auther:
 * @date:
 * @param:
 * @return:
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
    @Pointcut("execution(public * com.*.controller..*.*(..))||execution(public * com.*.web..*.*(..))")
    public void webLog() {
    }

    @Pointcut("execution(public * com.*.service..*.*(..))")
    public void serviceLog() {
    }

    @Pointcut("execution(public * com.*.repository..*.*(..))||execution(public * com.*.dao..*.*(..))")
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
        logger.info("Start Record Time : "+startTime.get());
        logger.info("OS:\t"+System.getProperty("os.name"));
        logger.info("IP:\t"+request.getRemoteAddr()+"\t port:"+request.getServerPort());
        logger.info("URL:\t"+request.getRequestURI());
        logger.info("Request Language : "+request.getContentType());
        logger.info("HTTP_METHOD: "+request.getMethod());
        logger.info("Cookies: "+request.getCookies());
        logger.info("Class_Method: "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        logger.info("Parameter Args: "+ Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "response",pointcut = "webLog()")
    public void doAfterReturning(Object response){
        // 处理完请求，返回内容
        logger.info("Response:[{}]", JSONObject.toJSONString(response));
        logger.info("End Time :[{}]ms ",(System.currentTimeMillis()-startTime.get()));
        logger.info("\n");
    }
}
