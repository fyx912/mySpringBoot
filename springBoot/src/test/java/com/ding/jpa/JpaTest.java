package com.ding.jpa;

import com.ding.domain.User;
import com.ding.repository.UserDao;
import com.ding.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
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
        Iterable<User> list = this.userDao.findAll();
        list.forEach(user -> System.out.println(user.getId()+"\t" +"\t"+user.getUserName()+
                "\t"+user.getName()+"\t"+user.getPassword()));
    }

    @Test
    public void findById(){
        User user = this.userDao.findById(1);
        System.out.println(user.getId()+"\t" +user.getUserName()+"\t"+user.getPassword());
    }

    @Test
    public void findPage() {
        Page<User> page = this.userDao.findAll(new PageRequest(0,5));
        for (User user : page){
            System.out.println(user.getId()+"\t" +user.getUserName()+"\t"+user.getPassword());
        }

    }


        @Test
    public void save(){
        User user = new User();
        user.setId(7);
        user.setUserName("fixe");
        user.setPassword("123456");
        user.setName("菲克斯");
        user.setPhone("13435873241");
        user.setAge(23);
        user.setDate(new Date());
        User user1 = this.userRepository.save(user);
        System.out.println();
        System.out.println(user1.getId()+"\t" +user1.getUserName()+"\t"+user1.getPassword());
    }

//    @Test
//    public void update(){
//        this.userDao.updateById(30,8);
//    }

//    @Test
//    public void update(){
//        User user = new User();
//        user.setId(7);
//        user.setUserName("fixe");
//        user.setPassword("123456");
//        user.setName("菲克斯");
//        user.setPhone("13435873241");
//        user.setAge(23);
//        user.setDate(new Date());
//        User user1 = this.userDao.save(user);
//        System.out.println();
//        System.out.println(user1.getId()+"\t" +user1.getUserName()+"\t"+user1.getPassword());
//    }

    @Test
    public void delete(){
        this.userRepository.deleteById(7);
    }




}
