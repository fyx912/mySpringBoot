package com.ding.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * rabbitmq 配置类
 * Direct Exchange：将消息中的Routing key与该Exchange关联的所有Binding中的Routing key进行比较，如果相等，则发送到该Binding对应的Queue中。
 * Topic Exchange：将消息中的Routing key与该Exchange关联的所有Binding中的Routing key进行对比，如果匹配上了，则发送到该Binding对应的Queue中。
 * Fanout Exchange：直接将消息转发到所有binding的对应queue中，这种exchange在路由转发的时候，忽略Routing key。
 * Headers Exchange：将消息中的headers与该Exchange相关联的所有Binging中的参数进行匹配，如果匹配上了，则发送到该Binding对应的Queue中。
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 持久化队列
     * queue可以有4个参数
     *          1、队列名
     *          2、durable 持久消息队列,rabbitmq重启的时候不需要创建新的队列，默认为true
     *          3、auto-delete 表示消息队列没有在使用时将被自动删除默认是false
     *          4、exclusive 表示该消息队列是否只在当前connection生效，默认为false
     */
    @Bean
    public Queue helloQueue() {
        return new Queue("hello", true);
    }

    @Bean
    public Queue HelloCallQueue() {
        return new Queue("hello-call");
    }

    @Bean
    public Queue neoQueue() {
        return new Queue("neo");
    }

    /**
     * 实例化交换机
     * @return
     */
    @Bean
    public DirectExchange directExchangeHello(){
        return new DirectExchange("helloExchange");
    }

    /**
     * 路由key绑定交换机
     * @param HelloCallQueue
     * @param directExchangeHello
     * @return
     */
    @Bean
    Binding bindingDirectExchangeHello(Queue HelloCallQueue,DirectExchange directExchangeHello) {
        return BindingBuilder.bind(HelloCallQueue).to(directExchangeHello).with("helloKey");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        Logger logger = LoggerFactory.getLogger(RabbitTemplate.class);
        //消息发送失败返回队列中,配置文件中publisher-returns:true
        rabbitTemplate.setMandatory(true);
        //消息返回，yml需要配置publisher-returns:true
        rabbitTemplate.setReturnCallback((message,replyCode,replyText, exchange,routingKey)->{
            String correlationId = message.getMessageProperties().getCorrelationId();
            logger.info("rabbitTemplate 消息:{},发送:{} ,原因:{} ,交换机:{}, 路由key:{}",correlationId,replyCode,replyText,exchange,routingKey);
        });
        //消息返回，yml需要配置publisher-confirm:true
        rabbitTemplate.setConfirmCallback(( correlationData,ack,cause)->{
            if (ack){
                String messageId = correlationData.getId();
                logger.info("消息发送exchange成功,id:{}",messageId);
            }else {
                logger.info("消息发送exchange失败,原因:{}",cause);
            }
        });
        return  rabbitTemplate;
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
