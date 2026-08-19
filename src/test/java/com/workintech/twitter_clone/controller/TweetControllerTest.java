package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.service.TweetService;
import com.workintech.twitter_clone.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TweetController.class,/*Securtiy Config olduğuiçin buna ihtiyaç duyduk.*/
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        })
class TweetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TweetService tweetService;
    @MockBean
    private UserService userService;
    @Test
    void findAll() throws Exception {
        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setEmail("ihsan@test.com");

        User tweetUser = new User();
        tweetUser.setId(2L);
        tweetUser.setUserName("ahmet");
        tweetUser.setEmail("ahmet@test.com");

        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Merhaba Twitter");
        tweet.setUser(tweetUser);

        when(tweetService.findAll()).thenReturn(List.of(tweet));
        when(userService.findByEmail("ihsan@test.com")).thenReturn(currentUser);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("ihsan@test.com", null)
        );

        mockMvc.perform(get("/tweet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].content").value("Merhaba Twitter"));
    }


    @Test
    void findById() throws Exception {
        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setEmail("ihsan@test.com");

        User tweetUser = new User();
        tweetUser.setId(2L);
        tweetUser.setUserName("ahmet");
        tweetUser.setEmail("ahmet@test.com");

        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Merhaba Twitter");
        tweet.setUser(tweetUser);
        tweet.setLikes(new ArrayList<>());
        tweet.setComments(new ArrayList<>());
        tweet.setRetweets(new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("ihsan@test.com", null)
        );

        when(tweetService.findById(tweet.getId())).thenReturn(tweet);
        when(userService.findByEmail("ihsan@test.com")).thenReturn(currentUser);

        mockMvc.perform(get("/tweet/findById/{id}", tweet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("Merhaba Twitter"));
    }

}