package com.example.userservice.service;

import com.example.userservice.models.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Minimalno rešenje
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Create a new user
    public User createUser(User user) {
        try {
            // Proveri da li je role null
            if (user.getRole() == null || user.getRole().getRoleId() == null) {
                throw new IllegalArgumentException("Role must be valid");
            }
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Error while saving user: " + e.getMessage());
            throw new RuntimeException("Error saving user", e);
        }
    }
    

    // Update an existing user
    public Optional<User> updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    // Delete a user
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }
}
