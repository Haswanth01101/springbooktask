package com.example.social_media_platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long followerId;
    private Long followingId;

    @ManyToOne
    @JoinColumn(name = "followerId", insertable = false, updatable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followingId", insertable = false, updatable = false)
    private User following;

    @CreationTimestamp
    @Column(name = "followed_at")
    private LocalDateTime followedAt;

    public void setFollower(User follower) {
        this.follower = follower;
        this.followerId = follower.getId();
    }

    public void setFollowing(User following) {
        this.following = following;
        this.followingId = following.getId();
    }

}