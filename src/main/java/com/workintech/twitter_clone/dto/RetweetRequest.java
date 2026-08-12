package com.workintech.twitter_clone.dto;

import jakarta.validation.constraints.Positive;

public record RetweetRequest(
        @Positive(message = "Tweet id pozitif olmalıdır")
        long tweetId
) {
}
