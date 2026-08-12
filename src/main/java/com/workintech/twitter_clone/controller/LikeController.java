package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.dto.LikeRequest;
import com.workintech.twitter_clone.dto.LikeResponse;

import com.workintech.twitter_clone.entity.Like;

import com.workintech.twitter_clone.entity.User;

import com.workintech.twitter_clone.service.LikeService;
import com.workintech.twitter_clone.service.UserService;
import com.workintech.twitter_clone.util.Converter;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Validated
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/like")
    LikeResponse save(@Valid @RequestBody LikeRequest likeRequest) {
        Like savedLike = likeService.save(likeRequest);
        return Converter.likeResponseConvert(savedLike);
    }
    @PostMapping("/dislike")
    LikeResponse delete(
            @RequestBody LikeRequest likeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        Like like = likeService.findByUserIdAndTweetId(user.getId(), likeRequest.tweetId());
        likeService.delete(like);
        return Converter.likeResponseConvert(like);
    }
}
