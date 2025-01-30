package com.fmi.comet.controller;

import com.fmi.comet.model.Message;
import com.fmi.comet.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/users/{userId}/friends/{friendId}/message")
    public ResponseEntity<Message> sendMessageToFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId,
            @RequestBody Message message) {
        try {
            Message sentMessage = messageService.sendMessageToFriend(userId, friendId, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.sendMessage(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<Message>> getMessagesByChannelId(@PathVariable Long channelId) {
        List<Message> messages = messageService.getMessagesByChannelId(channelId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Long userId) {
        List<Message> messages = messageService.getMessagesByUserId(userId);
        return ResponseEntity.ok(messages);
    }
}
