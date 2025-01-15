package com.fmi.comet.repository;
import com.fmi.comet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class UserDB {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // SQL query to insert a new user
    private static final String INSERT_USER = "INSERT INTO users (username, password, deleted) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";

    public void registerUser(User user) {
        jdbcTemplate.update(INSERT_USER, user.getUsername(), user.getPassword(), user.getDeleted());
    }

    public Optional<User> findByID(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID, new Object[]{id}, (rs, rowNum) ->
                Optional.of(new User(rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("deleted"))
                ));
    }
}

