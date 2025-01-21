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
    public void softDeleteUser(Integer id) {
        userRepository.markUserAsDeleted(id);
    }

    // Update user role
    public User updateRole(Integer id, User.Role role) {
        userRepository.updateUserRole(id, role);
        return userRepository.findUserById(id);
    }

    // Add a friend to a user
    public void addFriend(Integer userId, Integer friendId) {
        userRepository.addFriend(userId, friendId);
    }

    // Remove a friend from a user
    public void removeFriend(Integer userId, Integer friendId) {
        userRepository.removeFriend(userId, friendId);
    }

    // Get all friends for a user
    public List<User> getFriends(Integer userId) {
        return userRepository.findFriends(userId);
    }

    // Register a new user (with password encoding)
    public User registerUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Insert the new user into the database
        userRepository.insertUser(user);  // Use insertUser() instead of save()

        // Return the user with the updated ID (after insert)
        return userRepository.findUserById(user.getId());
    }
}
