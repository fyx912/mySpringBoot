package com.ding.services.many;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NeoReceiver {

    @RabbitListener(queues = "neo")
    @RabbitHandler
    public void process(String neo) {
        System.out.println("Receiver 1: " + neo);
    }

    @RabbitListener(queues = "neo")
    @RabbitHandler
    public void process2(String neo) {
        System.out.println("Receiver 2: " + neo);
    }


}
