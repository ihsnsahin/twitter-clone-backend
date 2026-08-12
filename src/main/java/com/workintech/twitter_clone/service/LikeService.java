package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.LikeRequest;
import com.workintech.twitter_clone.entity.Like;

public interface LikeService {
    Like findByUserIdAndTweetId(long userId,long tweetId);
    Like save(LikeRequest likeRequest);
    void delete(Like like);
}
