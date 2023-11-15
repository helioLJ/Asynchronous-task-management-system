package com.helio.taskManagement.factory;

import com.helio.taskManagement.model.Task;
import com.helio.taskManagement.model.User;
import org.springframework.stereotype.Component;

@Component
public class DefaultTaskFactory implements TaskFactory {
    @Override
    public Task createTask(String description) {
        Task task = new Task();
        task.setDescription(description);
        task.setCompleted(false);

        return task;
    }
}

