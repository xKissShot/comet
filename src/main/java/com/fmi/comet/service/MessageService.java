package com.fmi.comet.service;

import com.fmi.comet.model.Message;
import com.fmi.comet.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(Message message) {
        // Set the timestamp if it's not already set
        if (message.getTimestamp() == null) {
            message.setTimestamp(new Timestamp(System.currentTimeMillis())); // Set the current time as timestamp
        }
        // Save the message to the repository
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChannelId(Long channelId) {
        return messageRepository.findByChannelId(channelId);
    }

    // Add a method to retrieve messages for a specific user
    public List<Message> getMessagesByUserId(Long userId) {
        return messageRepository.findByUserId(userId); // Call the repository method to fetch messages by sender_id
    }
}
