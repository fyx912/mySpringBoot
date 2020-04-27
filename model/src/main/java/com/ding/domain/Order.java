package com.ding.domain;

import lombok.Data;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName Order
 * @date 2020-04-18 19:38
 */
@Data
public class Order {
    private  Integer id;
    private  String orderNo; //订单编号
    private  String  orderStatus;//订单状态
    private String payNo; //支付流水号
}
