package com.workintech.twitter_clone.dto;

public record LikeResponse(
        long id,
        long tweetId,
        long userId
) {
}
