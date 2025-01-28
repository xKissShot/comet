package com.fmi.comet.controller;

import com.fmi.comet.model.User;
import com.fmi.comet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);  // Ensure this returns the created user with an ID
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id) {  // Changed to Long
        userService.softDeleteUser(id);  // Changed to Long
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable Long id) {  // Changed to Long
        List<User> friends = userService.getFriends(id);  // Changed to Long
        return ResponseEntity.ok(friends);
    }

    @PostMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long id, @PathVariable Long friendId) {  // Changed to Long
        userService.addFriend(id, friendId);  // Changed to Long
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long id, @PathVariable Long friendId) {  // Changed to Long
        userService.removeFriend(id, friendId);  // Changed to Long
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<User> updateRole(@PathVariable Long id, @RequestParam String role) {  // Changed to Long
        User.Role newRole = User.Role.valueOf(role.toUpperCase());
        User updatedUser = userService.updateRole(id, newRole);  // Changed to Long
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Username and password must be provided.");
            }

            User createdUser = userService.registerUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
