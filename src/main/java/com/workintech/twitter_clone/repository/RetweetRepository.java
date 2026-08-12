package com.workintech.twitter_clone.repository;

import com.workintech.twitter_clone.entity.Like;
import com.workintech.twitter_clone.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    @Query("SELECT r from Retweet r WHERE r.user.id=:userId AND r.tweet.id=:tweetId")
    Optional<Retweet> findByUserIdAndTweetId(long userId, long tweetId);
}
