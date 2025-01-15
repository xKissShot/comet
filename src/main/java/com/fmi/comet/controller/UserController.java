package com.fmi.comet.controller;

import com.fmi.comet.model.User;
import com.fmi.comet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        Optional<User> loggedInUser = userService.findByID(user.getID());
        if (loggedInUser.isPresent()) {
            return ResponseEntity.ok(loggedInUser.get());
        }
        return ResponseEntity.status(401).body(null); // Unauthorized
    }
}

