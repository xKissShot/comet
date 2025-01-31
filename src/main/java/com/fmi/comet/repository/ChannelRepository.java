package com.fmi.comet.repository;

import com.fmi.comet.model.Channel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@Repository
public class ChannelRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChannelRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Channel> CHANNEL_ROW_MAPPER = (rs, rowNum) -> {
        Channel channel = new Channel();
        channel.setId(rs.getLong("id"));
        channel.setName(rs.getString("name"));
        channel.setOwnerId(rs.getLong("owner_id"));
        channel.setIsDeleted(rs.getBoolean("is_deleted"));
        return channel;
    };

    public Channel save(Channel channel) {
        if (channel.getIsDeleted() == null) {
            channel.setIsDeleted(false);
        }

        String sql = "INSERT INTO channels (name, owner_id, is_deleted) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, channel.getName(), channel.getOwnerId(), channel.getIsDeleted());

        Long id = jdbcTemplate.queryForObject("SELECT IDENT_CURRENT('channels')", Long.class);

        channel.setId(id);

        return channel;
    }

    public List<Channel> findByUserId(Long userId) {
        String sql = "SELECT * FROM channels WHERE owner_id = ? AND is_deleted = 0";
        return jdbcTemplate.query(sql, CHANNEL_ROW_MAPPER, userId);
    }

    public void deleteById(Long channelId) {
        String sql = "UPDATE channels SET is_deleted = 1 WHERE id = ?";
        jdbcTemplate.update(sql, channelId);
    }

    public boolean isOwner(Long channelId, Long userId) {
        String sql = "SELECT COUNT(*) FROM channels WHERE id = ? AND owner_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, channelId, userId);
        return count != null && count > 0;
    }

    public boolean isAdminOrOwner(Long channelId, Long userId) {
        String sql = """
            SELECT COUNT(*) FROM channel_members WHERE channel_id = ? AND user_id = ? AND (role = 'ADMIN' OR role = 'OWNER')
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, channelId, userId);
        return count != null && count > 0;
    }

    public void removeUserFromChannel(Long channelId, Long userId) {
        String sql = "DELETE FROM channel_members WHERE channel_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, channelId, userId);
    }

    public void addUserToChannel(Long channelId, Long userId, String role) {
        String sql = "INSERT INTO channel_members (channel_id, user_id, role) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, channelId, userId, role);
    }

    public boolean isUserInRole(Long channelId, Long userId, String role) {
        String sql = "SELECT COUNT(*) FROM channel_members WHERE channel_id = ? AND user_id = ? AND role = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, channelId, userId, role);
        return count != null && count > 0;
    }

}

