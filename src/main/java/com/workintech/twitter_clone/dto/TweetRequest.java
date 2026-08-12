package com.workintech.twitter_clone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TweetRequest(
        @NotBlank(message = "Tweet içeriği boş olamaz")
        @Size(max = 280, message = "Tweet en fazla 280 karakter olabilir")
        String content)
{
}
