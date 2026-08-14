package com.workintech.twitter_clone.dto;

public record RetweetResponse(
        long id,
        long tweetId,
        long userId
) { }
