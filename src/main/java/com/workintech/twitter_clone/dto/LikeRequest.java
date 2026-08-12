package com.workintech.twitter_clone.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LikeRequest(
        @Positive(message = "Tweet id pozitif olmalıdır")
        long tweetId
){

}
