package com.helio.taskManagement.factory;

import com.helio.taskManagement.model.Task;
import com.helio.taskManagement.model.User;

public interface TaskFactory {
    Task createTask(String description);
}
