package com.fmi.comet.repository;

import com.fmi.comet.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create or save a message
    public Message save(Message message) {
        String sql = "INSERT INTO messages (sender_id, channel_id, content, timestamp) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, message.getSenderId(), message.getChannelId(), message.getContent(), message.getTimestamp());

        // Retrieve the created message with the generated id
        String getIdSql = "SELECT id FROM messages WHERE sender_id = ? AND channel_id = ? AND content = ? LIMIT 1";
        Long id = jdbcTemplate.queryForObject(getIdSql, Long.class, message.getSenderId(), message.getChannelId(), message.getContent());
        message.setId(id);

        return message;
    }

    // Get messages by channel id
    public List<Message> findByChannelId(Long channelId) {
        String sql = "SELECT * FROM messages WHERE channel_id = ?";
        return jdbcTemplate.query(sql, new Object[]{channelId}, (rs, rowNum) -> {
            Message message = new Message();
            message.setId(rs.getLong("id"));
            message.setSenderId(rs.getLong("sender_id"));
            message.setChannelId(rs.getLong("channel_id"));
            message.setContent(rs.getString("content"));
            message.setTimestamp(rs.getTimestamp("timestamp"));
            return message;
        });
    }

    // Get messages by user (sender) id
    public List<Message> findByUserId(Long userId) {
        String sql = "SELECT * FROM messages WHERE sender_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            Message message = new Message();
            message.setId(rs.getLong("id"));
            message.setSenderId(rs.getLong("sender_id"));
            message.setChannelId(rs.getLong("channel_id"));
            message.setContent(rs.getString("content"));
            message.setTimestamp(rs.getTimestamp("timestamp"));
            return message;
        });
    }
}
