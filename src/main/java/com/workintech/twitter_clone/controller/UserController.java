package com.workintech.twitter_clone.controller;
import com.workintech.twitter_clone.dto.RetweetRequest;
import com.workintech.twitter_clone.dto.RetweetResponse;
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

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    public UserResponse findById(@Positive(message = "User id pozitif olmalıdır.") @PathVariable long id) {
        User user = userService.findById(id);
        return new UserResponse(user.getId(), user.getName());
    }
}
