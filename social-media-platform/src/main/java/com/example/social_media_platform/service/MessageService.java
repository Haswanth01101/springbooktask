package com.example.social_media_platform.service;

import com.example.social_media_platform.config.ChatWebSocketHandler;
import com.example.social_media_platform.entity.Message;
import com.example.social_media_platform.entity.User;
import com.example.social_media_platform.repository.MessageRepository;
import com.example.social_media_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);

        messageRepository.save(message);

        try {
            chatWebSocketHandler.sendMessageToUser(receiverId, content); // Send message to the specific receiver
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    public List<Message> getMessages(Long user1Id, Long user2Id) {
        userRepository.findById(user1Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User 1 not found"));

        userRepository.findById(user2Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User 2 not found"));

        return messageRepository.findBySenderIdAndReceiverId(user1Id, user2Id);
    }

    public List<Message> getMessagesBySender(Long senderId) {
        userRepository.findById(senderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));

        return messageRepository.findBySenderId(senderId);
    }

    public List<Message> getMessagesByReceiver(Long receiverId) {
        userRepository.findById(receiverId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));

        return messageRepository.findByReceiverId(receiverId);
    }

    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));

        messageRepository.delete(message);
    }
}