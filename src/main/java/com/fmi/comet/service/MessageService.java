package com.fmi.comet.service;

import com.fmi.comet.model.Message;
import com.fmi.comet.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            message.setTimestamp(new java.util.Date()); // Set the current time as timestamp
        }
        // Save the message to the repository
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChannelId(Long channelId) {
        return messageRepository.findByChannelId(channelId);
    }
}