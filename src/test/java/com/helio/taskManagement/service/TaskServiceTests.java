package com.helio.taskManagement.service;

import com.helio.taskManagement.model.Task;
import com.helio.taskManagement.model.User;
import com.helio.taskManagement.repository.TaskRepository;
import com.helio.taskManagement.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskServiceTests {

    @Autowired
    private TaskService taskService;
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateTask() {
        User assignedUser = new User(1L, "John Doe");
        Task newTask = new Task(1L, "Finish homework", false, assignedUser);

        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        Task createdTask = taskService.createTask(newTask);

        assertNotNull(createdTask);
        assertEquals(newTask.getDescription(), createdTask.getDescription());
    }

    @Test
    public void testAssignTask() {
        Long taskId = 1L;
        Long userId = 2L;

        User assignedUser = new User(userId, "John Doe");
        Task existingTask = new Task(taskId, "Finish homework", false, assignedUser);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(userRepository.findById(userId)).thenReturn(Optional.of(assignedUser));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        Task assignedTask = taskService.assignTask(taskId, userId);

        assertNotNull(assignedTask);
        assertEquals(assignedUser, assignedTask.getAssignedUser());
    }

    @Test
    public void testGetAllTasks() {
        User assignedUser = new User(1L, "John Doe");
        List<Task> tasks = Arrays.asList(new Task(1L, "Finish homework", false, assignedUser), new Task(2L, "Go to Mars", false, assignedUser));

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> retrievedTasks = taskService.getAllTasks();

        assertNotNull(retrievedTasks);
        assertEquals(tasks.size(), retrievedTasks.size());
    }

    @Test
    public void testToggleTask() {
        Long taskId = 1L;
        User assignedUser = new User(1L, "John Doe");
        Task existingTask = new Task(taskId, "Finish homework", false, assignedUser);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        Task toggledTask = taskService.toggleTask(taskId);

        assertNotNull(toggledTask);
        assertTrue(toggledTask.isCompleted());
    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testGetTaskById() {
        Long taskId = 1L;
        User assignedUser = new User(1L, "John Doe");
        Task task = new Task(taskId, "Finish homework", false, assignedUser);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> retrievedTask = taskService.getTaskById(taskId);

        assertTrue(retrievedTask.isPresent());
        assertEquals(task, retrievedTask.get());
    }
}
