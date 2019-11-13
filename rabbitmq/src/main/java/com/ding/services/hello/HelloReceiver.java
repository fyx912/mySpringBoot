package com.ding.services.hello;


import com.rabbitmq.client.Channel;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

public class HelloReceiver{


    @RabbitListener(queues = "hello")
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }


    @RabbitListener(queues ="hello-call")
    @RabbitHandler
    public void process(Message message, Channel channel ) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        System.out.println("Receiver  : " + new java.lang.String(message.getBody()));
    }


}
