package com.workintech.twitter_clone.dto;

public record RetweetResponse(
        long id,
        UserResponse retweetedBy,
        TweetCardResponse tweet
) { }
