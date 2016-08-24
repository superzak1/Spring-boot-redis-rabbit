package ru.rabbit.test;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by rakhmetov on 23/08/16.
 */
@SpringBootApplication
public class Application {

    @Value("${rabbit.route.key}")
    private String routingKey;
    @Value("${rabbit.exchange}")
    private String exchangeName;
    @Value("${rabbit.queue}")
    private String queueName;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*-----------------Rabbit config-----------------*/

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey(routingKey);
        rabbitTemplate.setExchange(exchangeName);
        return rabbitTemplate;
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    /*-----------------Redis config-----------------*/

    @Bean
    RedisTemplate<String, Integer> redisIntegerTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
