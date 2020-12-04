package com.ding.dao;

import com.ding.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName UserDao
 * @date 2020-11-12 14:56
 */
@Mapper
public interface UserDao {

    /**
     * 获取所有用户
     * @return
     */
    List<User> getAll();
    /**
     * 根据id获取用户
     * @return
     */
    User getOne(Long id);
    /**
     * 新增用户
     * @param user
     */
    Integer insertUser(User user);

    /**
     * 修改用户
     * @param user
     */
    Integer updateUser(User user);
    /**
     * 删除用户
     * @param id
     */
    Integer deleteUser(Integer id);
}
