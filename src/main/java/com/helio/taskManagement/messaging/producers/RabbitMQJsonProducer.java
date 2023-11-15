package com.helio.taskManagement.messaging.producers;

import com.helio.taskManagement.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(Task task) {
        LOGGER.info(String.format("\uD83D\uDC30 Json message sent -> %s \uD83D\uDCE4", task.toString()));
        rabbitTemplate.convertAndSend(exchangeName, routingJsonKey, task);
    }
}
