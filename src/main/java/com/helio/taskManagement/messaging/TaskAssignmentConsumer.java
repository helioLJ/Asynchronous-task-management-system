package com.helio.taskManagement.messaging;

import com.helio.taskManagement.dto.TaskAssignmentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.apache.logging.log4j.LogManager;


@Component
public class TaskAssignmentConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskAssignmentConsumer.class);

    @RabbitListener(queues = "task-assignment-queue")
    public void receiveTaskAssignmentMessage(TaskAssignmentMessage message) {
        LOGGER.info("Received Task Assignment: TaskId - {}, UserId - {}",
                message.getTaskId(), message.getUserId());
    }
}
