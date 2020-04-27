package com.ding.dao;

import com.ding.domain.Pay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PayDao {
    @Insert("insert into t_pay (payNo,payStatus,money) values(#{payNo},#{payStatus},#{money})")
    void save(Pay pay);
    @Update("update t_pay set  payStatus= #{payStatus}  where payNo=#{payNo}")
    void update(Pay pay);
    @Select("select  * from t_pay where payNo=#{payNo}")
    Pay findById(String payNo);
    @Select("select  * from t_pay")
    List<Pay> findAll();
}
