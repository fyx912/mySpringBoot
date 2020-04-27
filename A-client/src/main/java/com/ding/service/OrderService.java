package com.ding.service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ding.common.utils.HttpClientUtil;
import com.ding.common.utils.JsonResultUtils;
import com.ding.dao.OderDao;
import com.ding.domain.Order;
import com.ding.domain.Pay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName OrderService
 * @date 2020-04-18 20:26
 */
@Slf4j
@Service
public class OrderService {
    @Autowired
    private OderDao oderDao;
    String payService="http://127.0.0.1:8011";
    /**
     * 下订单
     * @return
     */
    public String orderExecute(Boolean payFlag){
        String payStatus = "failed";
        Order order = orderSave();//订单数据生成
        if (payFlag==true){
            String payPath ="?flag="+payFlag+"&orderNo="+order.getOrderNo();
            try {
                payStatus = HttpClientUtil.getMethod(payService+"/pay/save"+payPath);
                log.info("订单支付状态:{}",payStatus);
                JSONObject json = JSON.parseObject(payStatus);
                if (json.getString("code").equals("0")){
                    JSONObject jsonObject = (JSONObject) json.get("data");
                    this.updateOrderPayNo(order.getOrderNo(),jsonObject.getString("payNo"));
                    log.info("更新流水号");
                    Map map = orderAndPayInfo(order.getOrderNo());
                    return JsonResultUtils.success(map);
                }else {
                    log.info("订单数据进行回滚,更新订单状态:{}",payStatus);
                    this.orderFailed(order.getOrderNo());
                    return JsonResultUtils.failed();
                }
            }catch (Exception e){
                e.printStackTrace();
                log.info("订单数据进行回滚,更新订单状态:{}",payStatus);
                this.orderFailed(order.getOrderNo());
                return JsonResultUtils.failed();
            }
        }else {
            log.info("订单数据进行回滚,更新订单状态:{}",payStatus);
            this.orderFailed(order.getOrderNo());
            return JsonResultUtils.failed();
        }

    }

    @Transactional
    public Order  orderSave(){
        Order order = new Order();
        order.setOrderNo("N"+System.currentTimeMillis());
        order.setOrderStatus("success");
        order.setPayNo("");
        oderDao.saveOrder(order);
        log.info("订单生成成功,order=[{}]", JSON.toJSONString(order));
        return order;
    }

    @Transactional
    public Order  orderFailed(String orderNo){
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setOrderStatus("failed");
        oderDao.updateOrder(order);
        log.info("订单生成数据回滚,order=[{}]", JSON.toJSONString(order));
        return order;
    }

    /**
     * 处理成功更新流水号
     * @param orderNo
     * @param payNo
     * @return
     */
    @Transactional
    public Order  updateOrderPayNo(String orderNo,String payNo){
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setPayNo(payNo);
        oderDao.updateOrderPayNo(order);
        log.info("订单生成数据回滚,order=[{}]", JSON.toJSONString(order));
        return order;
    }

    /**
     * 订单和支付数据
     * @param orderNo
     * @return
     */
    public Map orderAndPayInfo(String orderNo){
        return oderDao.orderAndPayInfo(orderNo);
    }
}
