package com.fmi.comet.repository;

import com.fmi.comet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for User
    private static final RowMapper<User> USER_ROW_MAPPER = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));  // Changed to Long
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
        String sql = "SELECT * FROM users WHERE is_deleted = false";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER);
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO users (username, password, role, is_deleted, deleted_at) VALUES (?, ?, ?, false, null)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole().name());
    }

    public User findUserById(Long id) {  // Changed to Long
        String sql = "SELECT * FROM users WHERE id = ? AND is_deleted = false";  // Ensure you're retrieving only non-deleted users
        return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, id);
    }

    public void markUserAsDeleted(Long id) {  // Changed to Long
        String sql = "UPDATE users SET is_deleted = true, deleted_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, LocalDateTime.now(), id);
    }

    public void updateUserRole(Long id, User.Role role) {  // Changed to Long
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        jdbcTemplate.update(sql, role.name(), id);
    }

    public void addFriend(Long userId, Long friendId) {  // Changed to Long
        String sql = "INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {  // Changed to Long
        String sql = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    public List<User> findFriends(Long userId) {  // Changed to Long
        String sql = """
            SELECT u.* FROM users u
            INNER JOIN user_friends uf ON u.id = uf.friend_id
            WHERE uf.user_id = ? AND u.is_deleted = false
        """;
        return jdbcTemplate.query(sql, USER_ROW_MAPPER, userId);
    }
}
