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
    private final PasswordEncoder passwordEncoder;  // Declare PasswordEncoder

    // Inject UserRepository and PasswordEncoder through constructor
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;  // Initialize PasswordEncoder
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    // Add a new user
    public User addUser(User user) {
        // Hash the password before inserting
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Insert the user into the database
        userRepository.insertUser(user);  // Use insertUser() instead of save()

        // Return the user with the updated ID (after insert)
        return userRepository.findUserById(user.getId());
    }

    // Soft delete a user
    public void softDeleteUser(Long id) {  // Changed to Long
        userRepository.markUserAsDeleted(id);  // Changed to Long
    }

    // Update user role
    public User updateRole(Long id, User.Role role) {  // Changed to Long
        userRepository.updateUserRole(id, role);  // Changed to Long
        return userRepository.findUserById(id);  // Changed to Long
    }

    // Add a friend to a user
    public void addFriend(Long userId, Long friendId) {  // Changed to Long
        userRepository.addFriend(userId, friendId);  // Changed to Long
    }

    // Remove a friend from a user
    public void removeFriend(Long userId, Long friendId) {  // Changed to Long
        userRepository.removeFriend(userId, friendId);  // Changed to Long
    }

    // Get all friends for a user
    public List<User> getFriends(Long userId) {  // Changed to Long
        return userRepository.findFriends(userId);  // Changed to Long
    }

    // Register a new user (with password encoding)
    public User registerUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Insert the new user into the database
        userRepository.insertUser(user);  // Use insertUser() instead of save()

        // Return the user with the updated ID (after insert)
        return userRepository.findUserById(user.getId());  // Changed to Long
    }
}
