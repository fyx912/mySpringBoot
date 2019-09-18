package com.ding.common.api;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @Auther: ding
 * @Date: 2019-09-18 09:18
 * @Description: Api版本注解
 */
@Target(value = {METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
    //定义接口的版本号
    int value() default 1;
}
