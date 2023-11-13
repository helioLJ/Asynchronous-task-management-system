package com.helio.taskManagement.messaging;

import com.helio.taskManagement.dto.TaskAssignmentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskAssignmentProducer {
    private final AmqpTemplate amqpTemplate;
    public void sendTaskAssignmentMessage(Long taskId, Long userId) {
        TaskAssignmentMessage message = new TaskAssignmentMessage();
        message.setTaskId(taskId);
        message.setUserId(userId);

        amqpTemplate.convertAndSend("task-assignment-exchange", "task-assignment", message);
    }
}
