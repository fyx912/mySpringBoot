package com.ding.service;

import com.ding.common.utils.JsonResultUtils;
import com.ding.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: ding
 * @Date: 2019-06-16 04:59
 * @Description:
 */
@Component
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private List<User> userList = new ArrayList<>();

    public UserService(){init();};

    /**
     * 模拟数据
     */
    private void init(){
        userList.add(new User(1,"jack",20,"BJ"));
        userList.add(new User(2,"nick",21,"SH"));
        userList.add(new User(3,"marco",22,"TJ"));
        userList.add(new User(4,"jack",23,"NJ"));
        userList.add(new User(5,"nick",24,"CS"));
        userList.add(new User(6,"marco",25,"CQ"));
        userList.add(new User(7,"jack",26,"GZ"));
        userList.add(new User(8,"nick",27,"HK"));
        userList.add(new User(9,"marco",30,"HZ"));
    }

    @Cacheable(value = "user",key = "#name")
    public User findUser(String name) {
        logger.info("----find----" + name);
        for (User user : userList) {
            if (name.equals(user.getName())) {
                return user;
            }
        }
        return null;
    }

    @Cacheable(value = "users",key = "#id")
    public User findUserById(int id) {
        logger.info("----find----" + id);
        for (User user : userList) {
            if (id==user.getId()) {
                return user;
            }
        }
        return null;
    }

    @CachePut(value = "users",key = "#id")
    public User updateUserById(int id) {
        logger.info("----find----" + id);
        for (User user : userList) {
            if (id==user.getId()) {
                user.setId(1000);
                user.setDate(new Date());
                user.setPhone("131000000");
                user.setName("tinTin");
                user.setUsername("fyx912");
                user.setAge(35);
                user.setAddress("北京");
                return user;
            }
        }
        return null;
    }

    //清除一条缓存，key为要清空的数据
    @CacheEvict(value = "user",key="#id")
    public String deleteUser(int id) {
        logger.info("----find----" + id);
        for (User user : userList) {
            if (id==user.getId()) {
                userList.remove(user);
                return JsonResultUtils.success();
            }
        }
        return null;
    }
    //方法调用后清空所有缓存
    @CacheEvict(value = "user",allEntries = true)
    //方法调用前清空所有缓存
//    @CacheEvict(value = "user",beforeInvocation = true)
    public String deleteAll() {
        userList.clear();
        return JsonResultUtils.success();
    }

    @Cacheable(value = "user")
    public List<User> findUserAll() {
        return userList;
    }
}
