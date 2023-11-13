package com.helio.taskManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helio.taskManagement.model.User;
import com.helio.taskManagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User(1L, "John Doe");

        when(userService.createUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.username").value(newUser.getUsername()))
                        .andExpect(jsonPath("$.id").value(newUser.getId()));
    }
    @Test
    public void testGetUserById() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "John Doe");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.id").value(user.getId()));
    }
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "John Doe"),
                new User(2L, "Jane Smith")
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$[1].username").value(users.get(1).getUsername()));
    }
    @Test
    public void testUpdateUser() throws Exception {
        Long userId = 1L;
        User existingUser = new User(userId, "John Doe");
        User updatedUser = new User(userId, "Updated John Doe");

        when(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{userId}", userId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(userId);
    }

}
