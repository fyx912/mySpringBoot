package com.ding.services.many;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NeoSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendOne(int i) {
        String context = "spring boot neo queue"+" ****** "+i;
        System.out.println("Sender1 : " + context);
        this.amqpTemplate.convertAndSend("neo", context);
    }

    public void sendTwo(int i) {
        String context = "spring boot neo queue"+" ****** "+i;
        System.out.println("Sender2 : " + context);
        this.amqpTemplate.convertAndSend("neo", context);

        AMQP.BasicProperties
    }
}
