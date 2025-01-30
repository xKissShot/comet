package com.fmi.comet.repository;

import com.fmi.comet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<User> USER_ROW_MAPPER = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(User.Role.valueOf(rs.getString("role")));
            user.setIsDeleted(rs.getBoolean("is_deleted"));
            user.setDeletedAt(rs.getTimestamp("deleted_at") != null
                    ? rs.getTimestamp("deleted_at").toLocalDateTime()
                    : null);
            return user;
        }
    };

    public List<User> findAllUsers() {
        String sql = "SELECT * FROM users WHERE is_deleted = 0";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER);
    }

    @Transactional
    public void insertUser(User user) {
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        String sql = "INSERT INTO users (username, password, role, is_deleted, deleted_at) OUTPUT INSERTED.id VALUES (?, ?, ?, 0, NULL)";

        Long id = jdbcTemplate.queryForObject(sql, Long.class, user.getUsername(), user.getPassword(), user.getRole().name());

        if (id == null) {
            throw new RuntimeException("User ID could not be retrieved after insertion");
        }

        user.setId(id);

        System.out.println("User inserted with ID: " + user.getId());
    }

    public Optional<User> findUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ? AND is_deleted = 0";
        try {
            User user = jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, id);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND is_deleted = 0";
        try {
            User user = jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, username);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void markUserAsDeleted(Long id) {
        String sql = "UPDATE users SET is_deleted = 1, deleted_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, LocalDateTime.now(), id);
        System.out.println("User marked as deleted: " + id); // Log deletion
    }

    public void restoreUser(Long id) {
        String sql = "UPDATE users SET is_deleted = 0, deleted_at = NULL WHERE id = ?";
        jdbcTemplate.update(sql, id);
        System.out.println("User restored: " + id); // Log restoration
    }

    public void updateUserRole(Long id, User.Role role) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        jdbcTemplate.update(sql, role.name(), id);
        System.out.println("User role updated: " + id + " to " + role); // Log role update
    }

    public void addFriend(Long userId, Long friendId) {
        String sql = "INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
        System.out.println("Friend added: " + userId + " and " + friendId); // Log adding friend
    }

    public void removeFriend(Long userId, Long friendId) {
        String sql = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
        System.out.println("Friend removed: " + userId + " and " + friendId); // Log removing friend
    }

    public List<User> findFriends(Long userId) {
        String sql = """
            SELECT u.* FROM users u
            INNER JOIN user_friends uf ON u.id = uf.friend_id
            WHERE uf.user_id = ? AND u.is_deleted = 0
        """;
        return jdbcTemplate.query(sql, USER_ROW_MAPPER, userId);
    }
}
