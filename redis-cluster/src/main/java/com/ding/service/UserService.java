package com.ding.service;


import com.ding.dao.UserDao;
import com.ding.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName UserService
 * @date 2020-11-10 16:16
 */
@Service
@CacheConfig(cacheNames = {"userCache"})
public class UserService {

    @Resource
    private UserDao userDao;

    // 标志读取缓存操作，如果缓存不存在，则调用目标方法，并将结果放入缓存
    @Cacheable("user")
    public String  getAll() {
        System.out.println("缓存不存在，执行方法");
        List<User> list = userDao.getAll();
        try {
            return new ObjectMapper().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //如果缓存存在，直接读取缓存值；如果缓存不存在，则调用目标方法，并将结果放入缓存
    @Cacheable(cacheNames = {"user"},key = "#id",unless = "#result eq null")
    public User getOne(Long id){
        System.out.println("缓存不存在，执行方法,id="+id);
        return userDao.getOne(id);
    }

    //写入缓存，key为user.id,一般该注解标注在新增方法上
    @CachePut(cacheNames = { "user" }, key = "#user.id",unless="#result == null")
    public User insertUser(User user) {
        System.out.println("写入缓存");
        userDao.insertUser(user);
        return user;
    }

    /**
     * 注解@CachePut:一般用在添加和修改方法中
     * 既往数据库中添加一个新的对象,于此同时也往redis缓存中添加一个对应的缓存.
     * 这样可以达到缓存预热的目的.
     */
    @CachePut(cacheNames = { "user" }, key = "#user.id",unless="#result == null")
    public User updateUser(User user) {
        System.out.println("清除缓存");
        userDao.updateUser(user);
        return user;
    }

    /**
     * CacheEvict:一般用在删除方法中
     */
    @CacheEvict(cacheNames = { "user" }, key = "#id",beforeInvocation = true)//方法调用前清空所有缓存
    public void deleteOne(Integer id) {
        System.out.println("清除缓存,id="+id);
        userDao.deleteUser(id);
    }

    @CacheEvict(value="userCache",allEntries=true)//方法调用后清空所有缓存
    public void deleteAll1() {

    }

    @CacheEvict(value="userCache",beforeInvocation=true)//方法调用前清空所有缓存
    public void deleteAll2() {

    }

}
