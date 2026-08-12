package com.workintech.twitter_clone.dto;

import java.time.LocalDate;
import java.util.List;

public record TweetResponse (
        long id,
        String content,
        LocalDate tweetTime,
        long userId
) {
}
