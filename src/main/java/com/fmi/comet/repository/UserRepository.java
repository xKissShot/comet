package com.fmi.comet.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> findAllUsers() {
        String sql = "SELECT * FROM users WHERE is_deleted = false";
        return jdbcTemplate.queryForList(sql);
    }

    public void insertUser(Map<String, Object> user) {
        String sql = "INSERT INTO users (name, is_deleted) VALUES (?, false)";
        jdbcTemplate.update(sql, user.get("name"));
    }

    public void markUserAsDeleted(Long id) {
        String sql = "UPDATE users SET is_deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
