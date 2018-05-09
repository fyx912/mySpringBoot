package com.ding.service;


import com.ding.model.User;

import java.util.List;

public interface UserService {
    List<User> getUser();
    User getUserById(int id);
    Integer insertUser(User user);
    Integer updateUser( User user);
    Integer deleteUser(int id);
    Integer getUserId(int id);
    User isLogin( String userName, String password);

}
