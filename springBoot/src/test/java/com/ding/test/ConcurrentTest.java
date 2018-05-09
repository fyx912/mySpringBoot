package com.ding.test;


import com.ding.domain.User;
import com.ding.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConcurrentTest {
    private static int sum =800;
    private   CountDownLatch countDownLatch = new CountDownLatch(sum);
    @Autowired
    private UserService userMapper;

    @Test
    public void test(){
        long startTime = System.currentTimeMillis();
        for (int i=0;i<sum;i++){
            new Thread(new UserRequest(i)).start();
            countDownLatch.countDown();
        }
        System.out.println("End-time:"+(System.currentTimeMillis()-startTime));
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    protected   class UserRequest implements Runnable{
        private int count =0;

        public UserRequest(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<User> list = userMapper.getUser();
            list.forEach(user ->  System.out.println(user.getId()+"\t" +"\t"+user.getUsername()+
                    "\t"+user.getName()+"\t"+user.getPassword()));
            System.out.println(count+"----------------"+Thread.currentThread().getName());
        }
    }

    @Test
    public void testTime(){
        long startTime = System.currentTimeMillis();
        for (int i=0;i<sum;i++){
            List<User> list = userMapper.getUser();
            list.forEach(user ->  System.out.println(user.getId()+"\t" +"\t"+user.getUsername()+
                    "\t"+user.getName()+"\t"+user.getPassword()));
        }
        System.out.println("End-time:"+(System.currentTimeMillis()-startTime));
    }
}
