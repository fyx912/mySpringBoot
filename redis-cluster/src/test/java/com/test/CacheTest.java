package com.test;

import com.ding.domain.User;
import com.ding.service.UserService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author tintin
 * @version V1.0
 * @Description  缓存测试
 * @@copyright
 * @ClassName CacheTest
 * @date 2020-11-12 15:13
 */
public class CacheTest  extends TestBase {
    @Resource
    private UserService userService;

    @Test
    public void getAll(){
        for (int i = 0; i < 5; i++) {
            String result = userService.getAll();
            System.out.println(result);
        }

    }

    @Test
    public void getOne(){
        for (int i = 0; i < 5; i++) {
            User user = userService.getOne((long) 1);
            System.out.println(user);
        }

    }

    @Test
    public void insert(){
        User user = new User();
        user.setId(15);
        user.setUserName("tomcat1");
        user.setPassword("12345672");
        user.setName("tom喵");
        user.setAge(20);
        userService.insertUser(user);
    }

    @Test
    public void update(){
        User user = new User();
        user.setId(7);
        user.setUserName("tomcat");
        user.setPassword("1234567");
        user.setName("tom喵");
        user.setAge(20);
        userService.updateUser(user);
    }
}
