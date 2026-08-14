package com.workintech.twitter_clone.repository;

import com.workintech.twitter_clone.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l from Like l WHERE l.user.id=:userId AND l.tweet.id=:tweetId")
    Optional<Like> findByUserIdAndTweetId(long userId,long tweetId);
}
