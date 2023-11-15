package com.helio.taskManagement.observer;

import com.helio.taskManagement.model.Task;

public class LoggingObserver implements TaskObserver {
    @Override
    public void update(Task task) {
        System.out.println("Task updated: " + task.getDescription());
    }
}
