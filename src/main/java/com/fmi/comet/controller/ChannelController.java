package com.fmi.comet.controller;

import com.fmi.comet.model.Channel;
import com.fmi.comet.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    // Create a channel
    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        try {
            Channel savedChannel = channelService.createChannel(channel);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedChannel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get channels by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Channel>> getChannelsByUserId(@PathVariable Long userId) {
        List<Channel> channels = channelService.getChannelsByUserId(userId);
        return ResponseEntity.ok(channels);
    }

    // Delete a channel
    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long channelId) {
        try {
            channelService.deleteChannel(channelId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
