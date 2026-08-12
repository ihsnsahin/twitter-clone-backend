package com.workintech.twitter_clone.repository;

import com.workintech.twitter_clone.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
