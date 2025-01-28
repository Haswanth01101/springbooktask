package com.example.social_media_platform.service;

import com.example.social_media_platform.entity.Post;
import com.example.social_media_platform.entity.User;
import com.example.social_media_platform.repository.PostRepository;
import com.example.social_media_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserPostService {
    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> createPost(Long userId, MultipartFile file, String text) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        String fileName = file.getOriginalFilename();
        Post post = new Post();
        post.setUser(user);
        post.setImageUrl(fileName);
        post.setContent(text);
        postRepository.save(post);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Post created successfully");

        return jsonResponse;
    }

}


