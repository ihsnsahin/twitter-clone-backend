package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.TweetRequest;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exceptions.TwitterException;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {
    private TweetService tweetService;
    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        tweetService = new TweetServiceImpl(tweetRepository, userRepository);
    }

    @Test
    void findAll() {
        tweetService.findAll();
        verify(tweetRepository).findAll();
    }
    @Test
    void findById(){
        Tweet tweet = new Tweet();
        tweet.setContent("Merhaba Twitter!");
        given(tweetRepository.findById(tweet.getId())).willReturn(Optional.of(tweet));
        Tweet foundTweet = tweetService.findById(tweet.getId());
        assertThat(foundTweet).isNotNull()
                .extracting(Tweet::getContent)
                .isEqualTo("Merhaba Twitter!");

        verify(tweetRepository).findById(tweet.getId());
    }
    @Test
    void save() {
        //User oluştur.
        User user = new User();
        user.setUserName("ihsan");
        user.setEmail("ihsan@test.com");
        user.setPassword("123456");

        TweetRequest tweetRequest = new TweetRequest("Merhaba Twitter");
        //Authentication oluştur ve contexte koy
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "ihsan@test.com",
                        null
                );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        //Bağımlıkların metotlarının ne döndüreceğini belirliyoruz.
        given(userRepository.findByEmail(authentication.getName())).willReturn(Optional.of(user));
        given(tweetRepository.save(any(Tweet.class))).willReturn(new Tweet());
        //Testi yap
        tweetService.save(tweetRequest);
        //Metodun çağrılıp çağrılmadığını kontrol ediyoruz.
        verify(tweetRepository).save(any(Tweet.class));
    }
    @Test
    void delete() {
        //User oluştur.
        User user = new User();
        user.setUserName("ihsan");
        user.setEmail("ihsan@test.com");
        user.setPassword("123456");
        //Tweet oluştur.
        Tweet tweet = new Tweet();
        tweet.setContent("Merhaba Twitter!");
        tweet.setUser(user);
        //Authentication oluştur ve contexte koy
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "ihsan@test.com",
                        null
                );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        //
        tweetService.delete(tweet);
        verify(tweetRepository).delete(tweet);
    }
    @Test
    void delete_shouldThrowException_whenUserIsNotTweetOwner() {
        //User oluştur.
        User user = new User();
        user.setUserName("ihsan");
        user.setEmail("ihsan@test.com");
        user.setPassword("123456");
        //Tweet oluştur.
        Tweet tweet = new Tweet();
        tweet.setContent("Merhaba Twitter!");
        tweet.setUser(user);
        //Authentication oluştur ve contexte koy
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "ayse@test.com",
                        null
                );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        //
        assertThatThrownBy(()->tweetService.delete(tweet))
                .isInstanceOf(TwitterException.class)
                .hasMessageContaining("Tweeti yalnızca kendi yazarı silebilir.");
        verify(tweetRepository, never()).delete(tweet);
    }

}