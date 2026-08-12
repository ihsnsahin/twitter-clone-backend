package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.dto.TweetDetailResponse;
import com.workintech.twitter_clone.dto.TweetRequest;
import com.workintech.twitter_clone.dto.TweetResponse;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.service.TweetService;
import com.workintech.twitter_clone.service.UserService;
import com.workintech.twitter_clone.util.Converter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tweet")
@Validated
public class TweetController {
    private final TweetService tweetService;
    private final UserService userService;
    @GetMapping
    List<TweetResponse> findAll() {
        List<Tweet> tweetList = tweetService.findAll();
        return Converter.tweetResponseConvert(tweetList);
    }

    @GetMapping("findByUserId/{id}")
    List<TweetResponse> findByUserId(@Positive(message = "Kullanıcı id pozitif olmalıdır")
                                     @PathVariable long id) {

        User user = userService.findById(id);
        List<Tweet> tweetList = user.getTweets();
        return Converter.tweetResponseConvert(tweetList);
    }
    @GetMapping("findById/{id}")
    TweetDetailResponse findById(
            @Positive(message = "Tweet id pozitif olmalıdır")
            @PathVariable long id) {
        Tweet tweet = tweetService.findById(id);
        return Converter.tweetDetailResponse(tweet);
    }
    @PostMapping
    TweetResponse save( @Valid @RequestBody TweetRequest tweetRequest) {
        Tweet savedTweet = tweetService.save(tweetRequest);
        return Converter.tweetResponseConvert(savedTweet);
    }

    @PutMapping("/{id}")
    TweetResponse update(
            @Positive(message = "Tweet id pozitif olmalıdır")
            @PathVariable long id,
            @Valid @RequestBody TweetRequest tweetRequest) {

        Tweet foundTweet = tweetService.findById(id);
        foundTweet.setContent(tweetRequest.content());

        Tweet updatedTweet = tweetService.update(foundTweet);

        return Converter.tweetResponseConvert(updatedTweet);
    }
    @DeleteMapping("/{id}")
    TweetResponse delete(
            @Positive (message = "Tweet id pozitif olmalı")
            @PathVariable long id) {
        Tweet tweet = tweetService.findById(id);

        tweetService.delete(tweet);
        return Converter.tweetResponseConvert(tweet);
    }
}
