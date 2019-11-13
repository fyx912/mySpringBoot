package com.ding.services.hello;

import com.ding.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class HelloSender {
    private Logger logger = LoggerFactory.getLogger(HelloSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    int i = 0;

    public void send(int i) {
        String context = "hello " + new Date() + "\t "+i;
        System.out.println("Sender : " + context);
        this.amqpTemplate.convertAndSend("hello", context);
    }

    public void send(User user) {
        String context = user.toString()+"-->"+new Date() ;
        System.out.println("Sender  Object: " + context);
        this.amqpTemplate.convertAndSend("hello", context);
    }

    /**
     * 发送信息确认ack
     */
    public  void sendCall(int i){
        logger.info("mq消息发送开始===》");
        String context = "hello 序号: "+i;
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("hello", "helloKey",context,correlationData);
    }
}
