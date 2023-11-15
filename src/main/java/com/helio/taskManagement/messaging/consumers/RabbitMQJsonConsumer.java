package com.helio.taskManagement.messaging.consumers;

import com.helio.taskManagement.messaging.producers.RabbitMQJsonProducer;
import com.helio.taskManagement.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);
    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consume(Task task) {
        LOGGER.info(String.format("\uD83D\uDC30 Json message received -> %s \uD83D\uDCE9", task.toString()));
    }
}
