package com.ding.jpa;

import com.ding.domain.User;
import com.ding.repository.UserDao;
import com.ding.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JpaTest {

    @Autowired//自动从spring容器中加载userRepository
    private UserRepository userRepository;

    @Autowired//自动从spring容器中加载userRepository
    private UserDao userDao;


    @Test
    public void user(){
        Iterable<User> list = this.userRepository.findAll();
        list.forEach(user -> System.out.println(user.getId()+"\t" +"\t"+user.getUserName()+
                "\t"+user.getName()+"\t"+user.getPassword()));
    }

    @Test
    public void findById(){
        User user = this.userDao.findById(1);
        System.out.println(user.getId()+"\t" +user.getUserName()+"\t"+user.getPassword());
    }
}
