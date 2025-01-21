package com.example.social_media_platform.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    private Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Long userId = extractUserId(session);
        if (userId != null) {
            userSessions.put(userId, session);
            logger.info("User " + userId + " connected.");
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received message: " + message.getPayload());

        Long senderId = extractUserId(session);

        if (senderId != null) {
            handleUserMessage(senderId, message.getPayload());
        } else {
            logger.error("Invalid sender ID.");
        }
    }

    private void handleUserMessage(Long senderId, String message) throws Exception {
        String[] parts = message.split(":", 2);

        if (parts.length == 2) {
            try {
                Long recipientId = Long.parseLong(parts[0].trim());
                String messageContent = parts[1].trim();

                sendMessageToUser(recipientId, messageContent);
                logger.info("Sent message from User " + senderId + " to User " + recipientId + ": " + messageContent);
            } catch (NumberFormatException e) {
                logger.error("Invalid recipient ID in message: " + message);
            }
        } else {
            logger.error("Invalid message format: Expected 'userId:messageContent'");
        }
    }

    public void sendMessageToUser(Long userId, String message) throws Exception {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        } else {
            logger.error("User session not found or is closed for userId " + userId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        Long userId = extractUserId(session);
        if (userId != null) {
            userSessions.remove(userId);
            logger.info("User " + userId + " disconnected.");
        }
    }

    private Long extractUserId(WebSocketSession session) {
        String userIdStr = session.getUri().getQuery().split("=")[1];
        return Long.parseLong(userIdStr);
    }
}
