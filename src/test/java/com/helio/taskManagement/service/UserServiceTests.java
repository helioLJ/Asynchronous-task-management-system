package com.helio.taskManagement.service;

import com.helio.taskManagement.model.User;
import com.helio.taskManagement.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User newUser = new User(1L, "John Doe");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals(newUser.getUsername(), createdUser.getUsername());
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User(userId, "John Doe");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> retrievedUser = userService.getUserById(userId);

        assertTrue(retrievedUser.isPresent());
        assertEquals(user.getUsername(), retrievedUser.get().getUsername());
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = Arrays.asList(new User(1L, "John"), new User(2L, "Jane"));
        when(userRepository.findAll()).thenReturn(userList);

        List<User> allUsers = userService.getAllUsers();

        assertEquals(userList.size(), allUsers.size());
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        User existingUser = new User(userId, "John");
        User updatedUser = new User(userId, "Updated John");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getUsername(), result.getUsername());
    }

    @Test
    public void testUpdateUserNotFound() {
        Long userId = 1L;
        User updatedUser = new User(userId, "Updated John");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User result = userService.updateUser(userId, updatedUser);

        assertNull(result);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
