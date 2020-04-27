package com.ding.server;

import com.ding.common.utils.JsonResultUtils;
import com.ding.dao.PayDao;
import com.ding.domain.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName payService
 * @date 2020-04-18 18:33
 */
@Service
public class PayService {
    @Autowired
    private PayDao payDao;


    /**
     *  支付成功
     * @param orderNo 订单编号
     * @param money
     */
    @Transactional
    public String  paySave(String orderNo,String money) {
        if (StringUtils.isEmpty(orderNo)||StringUtils.isEmpty(money)){
            return JsonResultUtils.failed();
        }
        Pay pay = new Pay();
        pay.setMoney(money);
        pay.setPayStatus("success");
        orderNo = orderNo.substring(1,orderNo.length());
        pay.setPayNo("P"+orderNo);
        payDao.save(pay);
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return JsonResultUtils.success(pay);
    }

    /**
     *  修改支付状态为失败
     * @param payNo 支付流水号
     */
    @Transactional
    public String  payUpdate(String payNo){
        //处理结果
        String payStatus = "failed";
        if (StringUtils.isEmpty(payNo)){
            return JsonResultUtils.failed();
        }
        Pay pay = new Pay();
        pay.setPayStatus(payStatus);
        pay.setPayNo(payNo);
        payDao.save(pay);
        return JsonResultUtils.success(pay);
    }
}
