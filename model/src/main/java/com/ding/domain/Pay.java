package com.ding.domain;

import lombok.Data;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName Pay
 * @date 2020-04-18 19:38
 */
@Data
public class Pay {
    private  Integer id;
    private  String payNo; //支付流水号
    private  String  payStatus;//支付状态
    private  String  money;//支付金额
}
