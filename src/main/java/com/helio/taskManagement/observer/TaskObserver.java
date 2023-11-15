package com.helio.taskManagement.observer;

import com.helio.taskManagement.model.Task;

public interface TaskObserver {
    void update(Task task);
}
