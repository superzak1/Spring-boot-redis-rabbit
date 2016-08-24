package ru.rabbit.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rakhmetov on 23/08/16.
 */
@Service
public class RabbitService {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitService.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${rabbit.queue}")
    public void getMessage(String message) {
        LOG.info("Rabbit received message: " + message);
    }

    public void publishMessage(String message) {
        LOG.info("Rabbit published message: " + message);
        rabbitTemplate.convertAndSend(message);
    }
}
