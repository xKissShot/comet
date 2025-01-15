package com.fmi.comet.service;
import com.fmi.comet.model.User;
import com.fmi.comet.db.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDB userDB;

    public void registerUser(User user) {
        userDB.registerUser(user);
    }

    public Optional<User> findByID(Integer id) {
        return userDB.findByID(id);
    }

    // Additional business logic as required
}
