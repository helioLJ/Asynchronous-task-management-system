package com.helio.taskManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helio.taskManagement.model.Task;
import com.helio.taskManagement.model.User;
import com.helio.taskManagement.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;

    @Test
    public void testCreateTask() throws Exception {
        User assignedUser = new User(1L, "John Doe");
        Task newTask = new Task(1L, "Finish homework", false, assignedUser);

        when(taskService.createTask(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/api/tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTask)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.description").value(newTask.getDescription()));
    }

    @Test
    public void testAssignTask() throws Exception {
        Long taskId = 1L;
        Long userId = 2L;

        User assignedUser = new User(userId, "John Doe");
        Task assignedTask = new Task(taskId, "Finish homework", false, assignedUser);

        when(taskService.assignTask(taskId, userId)).thenReturn(assignedTask);

        mockMvc.perform(post("/api/tasks/{taskId}/assign/{userId}", taskId, userId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(assignedTask.getDescription()));
    }

    @Test
    public void testGetTaskById() throws Exception {
        Long taskId = 1L;
        User assignedUser = new User(1L, "John Doe");
        Task task = new Task(1L, "Finish homework", false, assignedUser);

        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(task.getDescription()));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        User assignedUser = new User(1L, "John Doe");
        List<Task> tasks = Arrays.asList(new Task(1L, "Finish homework", false, assignedUser), new Task(2L, "Go to mars", false, assignedUser));

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testToggleTask() throws Exception {
        Long taskId = 1L;
        User assignedUser = new User(1L, "John Doe");
        Task toggledTask = new Task(taskId, "Finish homework", false, assignedUser);
        when(taskService.toggleTask(taskId)).thenReturn(toggledTask);

        mockMvc.perform(put("/api/tasks/{taskId}/toggle", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(toggledTask.getDescription()));
    }

    @Test
    public void testDeleteTask() throws Exception {
        Long taskId = 1L;

        mockMvc.perform(delete("/api/tasks/{taskId}", taskId))
                .andExpect(status().isNoContent());
    }

    // Helper method to convert objects to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
