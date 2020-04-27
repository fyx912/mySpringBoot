package com.ding.web;

import com.ding.common.utils.HttpClientUtil;
import com.ding.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tintin
 * @version V1.0
 * @Description 订单服务
 * @@copyright
 * @ClassName OrderWeb
 * @date 2020-04-18 18:30
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderWeb {
    @Autowired
    private OrderService orderService;
    @GetMapping("save")
    public String order(@Param(value = "flag") boolean flag){
        return orderService.orderExecute(flag);
    }
}
