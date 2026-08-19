package com.workintech.twitter_clone.controller;
import com.workintech.twitter_clone.dto.RetweetRequest;
import com.workintech.twitter_clone.dto.RetweetResponse;
import com.workintech.twitter_clone.dto.TweetCardResponse;
import com.workintech.twitter_clone.dto.UserResponse;
import com.workintech.twitter_clone.entity.Retweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.service.RetweetService;
import com.workintech.twitter_clone.service.UserService;
import com.workintech.twitter_clone.util.Converter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/retweet")
@Validated
public class RetweetController {
    private final RetweetService retweetService;
    private final UserService userService;

    @GetMapping("/findByUserId/{id}")
    public List<RetweetResponse> findByUserId(@Positive @PathVariable long id) {
        User user = userService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        List<Retweet> retweetList = user.getRetweets();
        return Converter.retweetResponseConvert(retweetList,currentUser);
    }
    @PostMapping
    RetweetResponse save(@Valid @RequestBody RetweetRequest retweetRequest) {
        Retweet savedRetweet = retweetService.save(retweetRequest);
        //Tweet Card için ekleme yapıldı.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        return Converter.retweetResponseConvert(savedRetweet, currentUser);
    }

    @DeleteMapping("/{id}")
    RetweetResponse delete(
            @Positive(message = "Retweet id pozitif olmalıdır.")
            @PathVariable long id) {
        Retweet retweet = retweetService.findById(id);
        //Tweet Card için ekleme yapıldı.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByEmail(authentication.getName());
        retweetService.delete(retweet);
        return Converter.retweetResponseConvert(retweet, currentUser);
    }
}
