package com.fmi.comet.controller;

import com.fmi.comet.model.Channel;
import com.fmi.comet.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;
    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChannel(@RequestBody Channel channel) {
        if (channel.getName() == null || channel.getName().isEmpty()) {
            logger.warn("Channel name is required but not provided.");
            return ResponseEntity.badRequest().body("Channel name is required");
        }

        try {
            logger.info("Creating channel with name: {}", channel.getName());
            Channel savedChannel = channelService.createChannel(channel);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedChannel);
        } catch (Exception e) {
            logger.error("Error creating channel: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the channel");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Channel>> getChannelsByUserId(@PathVariable Long userId) {
        try {
            List<Channel> channels = channelService.getChannelsByUserId(userId);
            return ResponseEntity.ok(channels);
        } catch (Exception e) {
            logger.error("Error fetching channels for userId {}: ", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<String> deleteChannel(@PathVariable Long channelId, @RequestParam Long userId) {
        try {
            if (channelService.isUserChannelOwner(channelId, userId)) {
                channelService.deleteChannel(channelId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only channel owners can delete the channel");
            }
        } catch (Exception e) {
            logger.error("Error deleting channel with channelId {}: ", channelId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the channel");
        }
    }

    @DeleteMapping("/{channelId}/user/{userId}")
    public ResponseEntity<String> removeUserFromChannel(@PathVariable Long channelId, @PathVariable Long userId, @RequestParam Long targetUserId) {
        try {
            if (channelService.isUserAdminOrOwner(channelId, userId)) {
                channelService.removeUserFromChannel(channelId, targetUserId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins or owners can remove users");
            }
        } catch (Exception e) {
            logger.error("Error removing user from channel with channelId {}: ", channelId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the user");
        }
    }

    @PostMapping("/{channelId}/add-user")
    public ResponseEntity<String> addUserToChannel(@PathVariable Long channelId,
                                                   @RequestParam Long userId,
                                                   @RequestParam String role) {
        try {
            if (channelService.isUserAdminOrOwner(channelId, userId)) {
                channelService.addUserToChannel(channelId, userId, role);
                return ResponseEntity.status(HttpStatus.CREATED).body("User added to channel");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins or owners can add users");
            }
        } catch (Exception e) {
            logger.error("Error adding user to channel with channelId {}: ", channelId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the user");
        }
    }

    @DeleteMapping("/{channelId}/members/{userId}")
    public ResponseEntity<Void> removeGuestFromChannel(@PathVariable Long channelId, @PathVariable Long userId, @RequestParam Long requesterId) {
        if (!channelService.isUserAdminOrOwner(channelId, requesterId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String guestRole = "GUEST";
        if (!channelService.isUserInRole(channelId, userId, guestRole)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        channelService.removeUserFromChannel(channelId, userId);
        return ResponseEntity.noContent().build();
    }
}