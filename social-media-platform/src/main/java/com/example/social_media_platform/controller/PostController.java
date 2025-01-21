package com.example.social_media_platform.controller;

import com.example.social_media_platform.dto.PostRequestDto;
import com.example.social_media_platform.entity.Post;
import com.example.social_media_platform.service.PostService;
import com.example.social_media_platform.service.UserPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private UserPostService userPostService;


    @Autowired
    private PostService postService;


    @PostMapping("/{userId}/create")
    public ResponseEntity<String> createPost(
            @PathVariable Long userId,
            @RequestPart("file") MultipartFile file) {

        try {
            String response = userPostService.createPost(userId, file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post, @RequestParam Long userId) {
        Post createdPost = postService.createPost(userId, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }


    @DeleteMapping("deletePostById")
    public ResponseEntity<String> deletePost(@RequestHeader Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Deleted successfully");
    }


















//    @PostMapping("/create")
//    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post, @RequestParam Long userId) {
//        Post createdPost = postService.createPost(userId, post);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
//    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable Long userId) {
//        List<Post> posts = postService.getPostsByUser(userId);
//        return ResponseEntity.ok(posts);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
//        Post post = postService.getPostById(id);
//        return ResponseEntity.ok(post);
//    }
//





}


