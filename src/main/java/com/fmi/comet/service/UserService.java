package com.fmi.comet.service;

import com.fmi.comet.model.User;
import com.fmi.comet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;  // UserRepository is injected here
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    // Add a new user
    public User addUser(User user) {
        userRepository.insertUser(user);
        return userRepository.findUserById(user.getId());  // Return the user after inserting
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
}