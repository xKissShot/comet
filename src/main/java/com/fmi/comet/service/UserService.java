package com.fmi.comet.service;

import com.fmi.comet.model.User;
import com.fmi.comet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public User addUser(User user) {
        if (user.getRole() == null) {
            user.setRole(User.Role.USER); // Default to USER if no role is provided
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt password

        // Insert user and ensure they are properly registered
        userRepository.insertUser(user);

        // Check if the user was inserted and ID was generated
        return userRepository.findUserById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found after insertion"));
    }

    public void softDeleteUser(Long id) {
        userRepository.markUserAsDeleted(id);
    }

    public User updateRole(Long id, User.Role role) {
        userRepository.updateUserRole(id, role);
        return userRepository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void addFriend(Long userId, Long friendId) {
        userRepository.addFriend(userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        userRepository.removeFriend(userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        return userRepository.findFriends(userId);
    }

    public User registerUser(User user) {
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);  // Default role to USER
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Insert user and ensure they are properly registered
        userRepository.insertUser(user);

        return userRepository.findUserById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found after registration"));
    }
}
