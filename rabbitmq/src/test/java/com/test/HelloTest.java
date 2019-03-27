package com.test;


import com.ding.RabbitMQApplication;
import com.ding.model.User;
import com.ding.services.hello.HelloSender;
import org.junit.Test;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= RabbitMQApplication.class)
public class HelloTest {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void hello() throws Exception {
        helloSender.send();
    }

    @Test
    public void object() throws Exception {
        User user = new User("ding","123456");
        helloSender.send(user);
    }

}
