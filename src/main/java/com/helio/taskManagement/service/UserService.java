package com.helio.taskManagement.service;

import com.helio.taskManagement.model.User;
import com.helio.taskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        // Save the user to the database
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        // Retrieve a user by ID
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        // Retrieve all users from the database
        return userRepository.findAll();
    }

    public User updateUser(Long userId, User updatedUser) {
        // Find the user by ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update user properties
            existingUser.setUsername(updatedUser.getUsername());

            // Save the updated user
            return userRepository.save(existingUser);
        } else {
            // Handle user not found
            return null;
        }
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}