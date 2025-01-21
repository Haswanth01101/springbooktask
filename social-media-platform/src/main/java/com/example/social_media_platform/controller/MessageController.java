package com.example.social_media_platform.controller;

import com.example.social_media_platform.entity.Message;
import com.example.social_media_platform.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message messageRequest) {
        Message sentMessage = messageService.sendMessage(
                messageRequest.getSenderId(),
                messageRequest.getReceiverId(),
                messageRequest.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
    }


    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getMessages(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        List<Message> messages = messageService.getMessages(user1Id, user2Id);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<Message>> getMessagesBySender(@PathVariable Long senderId) {
        List<Message> messages = messageService.getMessagesBySender(senderId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<Message>> getMessagesByReceiver(@PathVariable Long receiverId) {
        List<Message> messages = messageService.getMessagesByReceiver(receiverId);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok("Deleted successfully");
    }
}

