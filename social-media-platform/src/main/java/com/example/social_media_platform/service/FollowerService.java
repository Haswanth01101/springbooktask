package com.example.social_media_platform.service;

import com.example.social_media_platform.entity.Follower;
import com.example.social_media_platform.entity.User;
import com.example.social_media_platform.repository.FollowerRepository;
import com.example.social_media_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class FollowerService {

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private UserRepository userRepository;

    public Follower followUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Follower not found"));

        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Following not found"));

        if (followerId.equals(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot follow yourself");
        }
        if (followerRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already following this user");
        }

        Follower followerEntity = new Follower();
        followerEntity.setFollower(follower);
        followerEntity.setFollowing(following);
        return followerRepository.save(followerEntity);
    }

    public String unfollowUser(Long followerId, Long followingId) {
        userRepository.findById(followerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Follower not found"));

        userRepository.findById(followingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Following not found"));

        Follower followerEntity = followerRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not following this user"));

        followerRepository.delete(followerEntity);
        return "Successfully unfollowed user with ID: " + followingId;
    }

    public List<User> getFollowers(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Long> followerIds = followerRepository.findFollowerIdsByFollowingId(userId);
        return userRepository.findAllById(followerIds);
    }

    public List<User> getFollowing(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Long> followingIds = followerRepository.findFollowingIdsByFollowerId(userId);
        return userRepository.findAllById(followingIds);
    }
}