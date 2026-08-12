package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.TweetRequest;
import com.workintech.twitter_clone.entity.Tweet;

import java.util.List;

public interface TweetService {
    List<Tweet> findAll();
    Tweet findById(long id);
    Tweet save(TweetRequest tweetRequest);
    void delete(Tweet tweet);
    Tweet update(Tweet tweet);
}
