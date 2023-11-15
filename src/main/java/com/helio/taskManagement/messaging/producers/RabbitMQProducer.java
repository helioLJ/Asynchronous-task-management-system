package com.helio.taskManagement.messaging.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private RabbitTemplate rabbitTemplate;
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendMessage(String message) {
        LOGGER.info(String.format("\uD83D\uDC30 Message sent -> %s \uD83D\uDCE4", message));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
