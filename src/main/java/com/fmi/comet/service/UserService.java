package com.fmi.comet.service;
import com.fmi.comet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Map<String, Object>> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public void addUser(Map<String, Object> user) {
        userRepository.insertUser(user);
    }

    public void softDeleteUser(Long id) {
        userRepository.markUserAsDeleted(id);
    }
}
