package com.workintech.twitter_clone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterResponse (
        String userName,
        String message) {
}
