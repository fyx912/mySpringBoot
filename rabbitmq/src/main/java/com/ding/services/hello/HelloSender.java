package com.ding.services.hello;

import com.ding.model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    public void send(User user) {
        String context = user.toString()+"-->"+new Date() ;
        System.out.println("Sender  Object: " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }
}
