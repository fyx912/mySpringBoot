package com.ding.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    /**
     * 持久化队列
     */
    @Bean
    public Queue queue() {
        return new Queue("hello", true);
    }

    @Bean
    public Queue neoQueue() {
        return new Queue("neo");
    }


    /**
     * 延时队列，死信队列
     * @return
     */
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable("delayQueue")                      //队列名称
                .withArgument("x-message-ttl",10000)                      //死信时间
                .withArgument("x-dead-letter-exchange", "")            //死信重新投递的交换机
                .withArgument("x-dead-letter-routing-key", "queue")//路由到队列的routingKey
                .build();
    }

}
