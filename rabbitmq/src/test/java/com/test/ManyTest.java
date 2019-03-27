package com.test;

import com.ding.RabbitMQApplication;
import com.ding.services.many.NeoSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQApplication.class)
public class ManyTest {
    @Autowired
    private NeoSender neoSender;

    @Test
    public void oneToMany(){
        for (int i = 0; i <100 ; i++) {
            neoSender.sendOne(i);
        }
    }

    @Test
    public void manyToOne(){
        for (int i = 0; i <100 ; i++) {
            neoSender.sendOne(i);
            neoSender.sendTwo(i);
        }
    }
}
