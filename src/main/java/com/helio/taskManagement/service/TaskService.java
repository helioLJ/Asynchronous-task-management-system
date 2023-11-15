package com.helio.taskManagement.service;

import com.helio.taskManagement.messaging.producers.RabbitMQJsonProducer;
import com.helio.taskManagement.messaging.producers.RabbitMQProducer;
import com.helio.taskManagement.model.Task;
import com.helio.taskManagement.model.User;
import com.helio.taskManagement.repository.TaskRepository;
import com.helio.taskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RabbitMQProducer rabbitMQProducer;
    @Autowired
    private RabbitMQJsonProducer rabbitMQJsonProducer;

    @Async
    public Task createTask(Task task) {
//        System.out.println("Async method start: " + Thread.currentThread().getName());
//        try {
//            // Simulate a time-consuming task
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Async method end: " + Thread.currentThread().getName());

        return taskRepository.save(task);
    }

    public Task assignTask(Long taskId, Long userId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        User assignedUser = userRepository.findById(userId).orElse(null);

        if (optionalTask.isEmpty() || assignedUser == null) {
            return null;
        }

        Task task = optionalTask.get();
        task.setAssignedUser(assignedUser);
        String message = String.format("Hey, %s was assigned with Task %s", assignedUser.getUsername(), task.getDescription());
        rabbitMQProducer.sendMessage(message);
        rabbitMQJsonProducer.sendJsonMessage(task);

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task toggleTask(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isEmpty()) {
            return null;
        }

        Task task = optionalTask.get();

        task.setCompleted(!task.isCompleted());

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }
}
