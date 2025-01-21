package com.example.social_media_platform.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    @CreationTimestamp
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "senderId",insertable = false,updatable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverId",insertable = false,updatable = false)
    private User receiver;

    public void setReceiver(User receiver) {
        this.receiver = receiver;
        if (receiver != null) {
            this.receiverId = receiver.getId();
        }
    }

    public void setSender(User sender) {
        this.sender = sender;
        if (sender != null) {
            this.senderId = sender.getId();
        }
    }


    public void setContent(String content) {
        this.content = content;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}