package com.workintech.twitter_clone.dto;

import java.time.LocalDate;

public record CommentResponse(
        long id,
        String content,
        LocalDate commentTime,
        long tweetId,
        long userId,
        String userName
) {
}
