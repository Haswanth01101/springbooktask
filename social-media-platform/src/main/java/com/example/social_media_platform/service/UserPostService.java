package com.example.social_media_platform.service;

import com.example.social_media_platform.dto.PostRequestDto;
import com.example.social_media_platform.entity.Post;
import com.example.social_media_platform.entity.User;
import com.example.social_media_platform.repository.PostRepository;
import com.example.social_media_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserPostService {
    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public String createPost(Long userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        String fileName = file.getOriginalFilename();
        Post post = new Post();
        post.setUser(user);
        post.setImageUrl(fileName);
        postRepository.save(post);
        return "Post created successfully for user ID: " + userId;
    }
}

