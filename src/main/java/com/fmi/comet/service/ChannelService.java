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
        channelRepository.delete(channelId);
    }
}
