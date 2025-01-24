package com.fmi.comet.repository;

import com.fmi.comet.model.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save the message to the database
    public Message save(Message message) {
        // SQL to insert a new message
        String sql = "INSERT INTO messages (sender_id, channel_id, content, timestamp) VALUES (?, ?, ?, ?)";

        // Insert the message into the database
        jdbcTemplate.update(sql, ps -> {
            ps.setLong(1, message.getSenderId());
            ps.setLong(2, message.getChannelId());
            ps.setString(3, message.getContent());
            ps.setTimestamp(4, new java.sql.Timestamp(message.getTimestamp().getTime()));
        });

        // Return the saved message with an ID set after insert (assumes auto-increment primary key)
        return message;
    }

    // Find messages by channel ID
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
}
