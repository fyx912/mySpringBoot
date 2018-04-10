package com.ding.test;


import com.ding.domain.User;
import com.ding.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConcurrentTest {
    private static int sum =20;
    @Autowired
    private  UserService userService;
    private CountDownLatch countDownLatch = new CountDownLatch(sum);

    @Test
    public void test(){
        for (int i=0;i<sum;i++){
            new Thread(new UserRequset()).start();
            countDownLatch.countDown();
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class UserRequset implements Runnable{
        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            User user = this.userService.getUserById(1);
//            System.out.println(user.getId()+"\t" +user.getUserName()+"\t"+user.getPassword());
        }
    }
}
