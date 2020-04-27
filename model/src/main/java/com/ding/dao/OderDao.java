package com.ding.dao;

import com.ding.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface OderDao {

    @Insert("insert into t_order (orderNo,orderStatus) values(#{orderNo},#{orderStatus})")
    void saveOrder(Order order);
    @Update("update t_order set  orderStatus= #{orderStatus} ,payNo=#{payNo} where orderNo=#{orderNo}")
    void updateOrder(Order order);

    @Update("update t_order set payNo=#{payNo} where orderNo=#{orderNo}")
    void updateOrderPayNo(Order order);

    @Select("select  * from t_order where orderNo=#{orderNo}")
    Order findById(String orderNo);
    @Select("select  * from t_order")
    List<Order> findAll();

    /**
     * 订单和支付数据
     * @param orderNo
     * @return
     */
    @Select("select o.*,p.* from t_order o,t_pay p where o.payNo=p.payNo and orderNo=#{orderNo}")
    Map orderAndPayInfo(String orderNo);
}
