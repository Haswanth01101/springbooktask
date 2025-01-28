package com.example.social_media_platform.controller;

import com.example.social_media_platform.entity.User;
import com.example.social_media_platform.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/followers")

public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestParam Long followerId, @RequestParam Long followingId) {
        followerService.followUser(followerId, followingId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully followed user with ID: " + followingId);
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam Long followerId, @RequestParam Long followingId) {
        followerService.unfollowUser(followerId, followingId);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully unfollowed user with ID: " + followingId+ " at " + LocalDateTime.now());
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Long userId) {
        List<User> followers = followerService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<User>> getFollowing(@PathVariable Long userId) {
        List<User> following = followerService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }
}