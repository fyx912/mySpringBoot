package com.ding.common.api;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: ding
 * @Date: 2019-09-18 09:42
 * @Description:
 */

public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    private Logger log = LoggerFactory.getLogger(ApiVersionCondition.class);
    //从URL中提取版本部分，例如：[v0-9]
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");

    private int apiVersion;

    public  ApiVersionCondition(int apiVersion){
        log.info("ApiVersionCondition apiVersion ==>{} ", apiVersion);
        log.info("ApiVersionCondition Init...");
        this.apiVersion = apiVersion;
    }


    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(other.apiVersion);
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        // 正则匹配请求的uri，看是否有版本号 v1
        Matcher matcher = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (matcher.find()) {
            Integer version = Integer.valueOf(matcher.group(1));
            // 超过当前最大版本号或者低于最低的版本号均返回不匹配
            if (version >= this.apiVersion) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 以版本号大小判定优先级，越高越优先
        return other.apiVersion - this.apiVersion;
    }


    public int getApiVersion() {
        return apiVersion;
    }
}
