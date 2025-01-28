package com.example.social_media_platform.repository;

import com.example.social_media_platform.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Optional<Follower> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Query("SELECT f.follower.id FROM Follower f WHERE f.following.id = :followingId")
    List<Long> findFollowerIdsByFollowingId(@Param("followingId") Long followingId);

    @Query("SELECT f.following.id FROM Follower f WHERE f.follower.id = :followerId")
    List<Long> findFollowingIdsByFollowerId(@Param("followerId") Long followerId);
}