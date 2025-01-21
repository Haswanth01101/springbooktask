package com.example.social_media_platform.repository;

import com.example.social_media_platform.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Message> findByReceiverId(Long receiverId);

    List<Message> findBySenderId(Long senderId);
}
