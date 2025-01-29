package com.fmi.comet.service;

import com.fmi.comet.model.Channel;
import com.fmi.comet.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel createChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    public List<Channel> getChannelsByUserId(Long userId) {
        return channelRepository.findByUserId(userId);
    }

    public void deleteChannel(Long channelId) {
        channelRepository.deleteById(channelId);
    }

    public boolean isUserChannelOwner(Long channelId, Long userId) {
        return channelRepository.isOwner(channelId, userId);
    }

    public boolean isUserAdminOrOwner(Long channelId, Long userId) {
        return channelRepository.isAdminOrOwner(channelId, userId);
    }

    public void removeUserFromChannel(Long channelId, Long userId) {
        channelRepository.removeUserFromChannel(channelId, userId);
    }
}
