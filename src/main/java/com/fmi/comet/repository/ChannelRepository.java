package com.fmi.comet.repository;

import com.fmi.comet.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ChannelRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChannelRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create a new channel
    public Channel save(Channel channel) {
        String sql = "INSERT INTO channels (name, owner_id, is_deleted) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, channel.getName(), channel.getOwnerId(), channel.getIsDeleted());

        // Retrieve the created channel with the generated id
        String getIdSql = "SELECT id FROM channels WHERE name = ? AND owner_id = ? LIMIT 1";
        Long id = jdbcTemplate.queryForObject(getIdSql, Long.class, channel.getName(), channel.getOwnerId());
        channel.setId(id);

        return channel;
    }

    // Get channels by user id
    public List<Channel> findByUserId(Long userId) {
        String sql = "SELECT * FROM channels WHERE owner_id = ? AND is_deleted = false";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Channel channel = new Channel();
            channel.setId(rs.getLong("id"));
            channel.setName(rs.getString("name"));
            channel.setOwnerId(rs.getLong("owner_id"));
            channel.setIsDeleted(rs.getBoolean("is_deleted"));
            return channel;
        }, userId);
    }

    // Delete a channel
    public void delete(Long channelId) {
        String sql = "UPDATE channels SET is_deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, channelId);
    }
}
