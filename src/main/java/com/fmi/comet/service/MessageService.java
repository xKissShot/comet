package com.fmi.comet.service;

import com.fmi.comet.model.Message;
import com.fmi.comet.model.MessageType;
import com.fmi.comet.repository.MessageRepository;
import com.fmi.comet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message sendMessage(Message message) {
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }

        if (message.getTimestamp() == null) {
            message.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }

        if (message.getChannelId() != null) {
            message.setMessageType(MessageType.CHANNEL); // For channel messages
        } else {
            message.setMessageType(MessageType.DIRECT); // For direct messages (null channelId)
        }

        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChannelId(Long channelId) {
        return messageRepository.findByChannelId(channelId);
    }

    public List<Message> getMessagesByUserId(Long userId) {
        return messageRepository.findByUserId(userId);
    }

    public Message sendMessageToFriend(Long userId, Long friendId, Message message) {
        if (!isFriend(userId, friendId)) {
            throw new IllegalArgumentException("The users are not friends.");
        }

        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }

        message.setSenderId(userId);
        message.setReceiverId(friendId); // Make sure to store the receiverId for direct messages
        message.setChannelId(null); // For direct messages, channelId should be null
        message.setMessageType(MessageType.DIRECT); // Set message type as DIRECT for friend-to-friend messages

        if (message.getTimestamp() == null) {
            message.setTimestamp(new Timestamp(System.currentTimeMillis())); // Set timestamp if not provided
        }

        return messageRepository.save(message);
    }

    private boolean isFriend(Long userId, Long friendId) {
        return userRepository.isFriend(userId, friendId);
    }
}
