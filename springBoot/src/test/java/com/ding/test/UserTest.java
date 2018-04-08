package com.ding.test;

import com.ding.domain.User;
import com.ding.mapper.UserMapper;
import com.ding.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userMapper;

    @Test
    public void user(){
        List<User> list = this.userMapper.getUser();
        for (User user:list) {
            System.out.println(user.getId()+"\t" +"\t"+user.getUserName()+
                    "\t"+user.getName()+"\t"+user.getPassword());
        }

    }

    @Test
    public void getUserById(){
       User user = this.userMapper.getUserById(1);
       System.out.println(user.getId()+"\t" +user.getUserName()+"\t"+user.getPassword());
    }

    @Test
    public  void insertUser(){
        User user = new User();
        user.setId(7);
        user.setUserName("fixe");
        user.setName("菲克斯");
        user.setPassword("123456");
        user.setAge(31);
        user.setPhone("13532550872");
        user.setDate(new Date());
        int id = this.userMapper.insertUser(user);
        System.out.println("id=\t"+id);
    }

    @Test
    public  void updateUser(){
        User user = new User();
        user.setId(5);
        user.setUserName("Jon");
        user.setName("乔恩");
        user.setPassword("654321");
        user.setAge(24);
        user.setPhone("14354329861");
        int id = this.userMapper.updateUser(user);
        System.out.println("id=\t"+id);
    }

    @Test
    public void delete(){
        int id = this.userMapper.deleteUser(7);
        System.out.println("id=\t"+id);
    }


}
