package com.ding.web;

import com.ding.server.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tintin
 * @version V1.0
 * @Description 支付
 * @@copyright
 * @ClassName PayWeb
 * @date 2020-04-18 18:31
 */
@Slf4j
@RestController
@RequestMapping("pay")
public class PayWeb {
    @Autowired
    private PayService payService;

    /**
     *  支付
     * @param flag  true 支付成功 flase 失败
     * @param orderNo 订单编号
     * @return
     */
    @GetMapping("save")
    public String  pay(@Param("flag") boolean flag,@Param("orderNo") String orderNo){
        String payStatus = "failed";
        if (!StringUtils.isEmpty(orderNo)){
            if (flag){
                payStatus = payService.paySave(orderNo,"$100.00");
            }
        }
        log.info("支付服务,当前订单:{}支付状态:{}",orderNo,payStatus);
        return payStatus;
    }
}
