package com.ding.service;


import com.ding.mapper.UserMapper;
import com.ding.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Cacheable("user")
    @Override
    public List<User> getUser() {
        this.simulateSlowService();
        return this.userMapper.getUser();
    }

    @Override
    public User getUserById(int id) {
        return this.userMapper.getUserById(id);
    }

    @Override
    public Integer insertUser(User user) {
        return this.userMapper.insertUser(user);
    }

    @Override
    public Integer updateUser(User user) {
        return this.userMapper.updateUser(user);
    }

    @Override
    public Integer deleteUser(int id) {
        return this.userMapper.deleteUser(id);
    }

    @Override
    public Integer getUserId(int id) {
        return this.userMapper.getUserId(id);
    }

    @Override
    public User isLogin(String userName, String password) {
        return this.userMapper.isLogin(userName,password);
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
