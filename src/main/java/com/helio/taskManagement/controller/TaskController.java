package com.helio.taskManagement.controller;

import com.helio.taskManagement.model.Task;
import com.helio.taskManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @PostMapping("/")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return new ResponseEntity(taskService.createTask(task), HttpStatus.CREATED);
    }
    @PostMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<Optional<Task>> assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
        Optional<Task> task = Optional.ofNullable(taskService.assignTask(taskId, userId));
        if (task.isEmpty()) {
            return new ResponseEntity("Task/ User not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.CREATED);
    }
    @GetMapping("/{taskId}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity(taskService.getAllTasks(), HttpStatus.OK);
    }
    @PutMapping("/{taskId}/toggle")
    public ResponseEntity<Optional<Task>> toggleTask(@PathVariable Long taskId) {
        Optional<Task> task = Optional.ofNullable(taskService.toggleTask(taskId));
        if (task.isEmpty()) {
            return new ResponseEntity("Task not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
