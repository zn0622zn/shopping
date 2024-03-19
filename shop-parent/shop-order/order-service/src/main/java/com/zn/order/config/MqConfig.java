package com.zn.order.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 张男
 * @date: 2024/2/13---14:13
 */
@Configuration
public class MqConfig {
    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(this.connectionFactory());
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("order.direct.exchange");
    }

    /**
     * 订单30min未支付过期
     *
     * @return
     */
    @Bean
    public Queue ttlQueue() {
        Queue queue = new Queue("order.ttl.queue");
        queue.addArgument("x-message-ttl", 1800_000);//30min过期
        queue.addArgument("x-dead-letter-exchange", "order.direct.exchange");
        queue.addArgument("x-dead-letter-routing-key", "order.dead.key");
        return queue;
    }

    @Bean
    public Queue deadQueue() {
        return new Queue("dead.queue");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(this.ttlQueue()).to(this.directExchange()).with("to.ttl.key");
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(this.deadQueue()).to(this.directExchange()).with("order.dead.key");
    }

}
