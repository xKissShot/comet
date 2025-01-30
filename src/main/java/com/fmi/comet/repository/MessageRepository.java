package com.fmi.comet.repository;

import com.fmi.comet.model.Message;
import com.fmi.comet.model.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class MessageRepository {

    private static final Logger logger = LoggerFactory.getLogger(MessageRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Message save(Message message) {
        logger.debug("Inserting message: sender_id={}, channel_id={}, content={}, timestamp={}, messageType={}",
                message.getSenderId(), message.getChannelId(), message.getContent(), message.getTimestamp(), message.getMessageType());

        String sql = "INSERT INTO messages (sender_id, channel_id, content, timestamp, message_type) VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, message.getSenderId(), message.getChannelId(), message.getContent(), message.getTimestamp(), message.getMessageType().toString());

            String getIdSql = "SELECT SCOPE_IDENTITY()";
            Long id = jdbcTemplate.queryForObject(getIdSql, Long.class);

            message.setId(id);
            return message;
        } catch (Exception e) {
            logger.error("Error inserting message: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Message> findByChannelId(Long channelId) {
        String sql = "SELECT * FROM messages WHERE channel_id = ? AND message_type = 'CHANNEL'";
        return jdbcTemplate.query(sql, new Object[]{channelId}, new MessageRowMapper());
    }

    public List<Message> findByUserId(Long userId) {
        String sql = "SELECT * FROM messages WHERE sender_id = ? AND message_type = 'DIRECT'";
        return jdbcTemplate.query(sql, new Object[]{userId}, new MessageRowMapper());
    }

    private static class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            Message message = new Message();
            message.setId(rs.getLong("id"));
            message.setSenderId(rs.getLong("sender_id"));
            message.setChannelId(rs.getLong("channel_id"));
            message.setContent(rs.getString("content"));
            message.setTimestamp(rs.getTimestamp("timestamp"));

            // Map message_type from the database to MessageType enum
            message.setMessageType(MessageType.valueOf(rs.getString("message_type")));

            return message;
        }
    }
}
