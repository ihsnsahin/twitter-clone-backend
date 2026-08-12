package com.workintech.twitter_clone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CommentRequest(
        @NotBlank(message = "Comment içeriği boş olamaz")
        @Size(max = 280, message = "Comment en fazla 280 karakter olabilir")
        String content,
        @Positive(message = "Tweet id pozitif olmalıdır")
        long tweetId
)
 {
}
