package com.workintech.twitter_clone.repository;

import com.workintech.twitter_clone.entity.Tweet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class TweetRepositoryTest {
    private TweetRepository tweetRepository;
    private Tweet tweet1;
    private Tweet tweet2;
    @Autowired
    public TweetRepositoryTest(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @BeforeEach
    void setUp() {
        tweet1 = new Tweet();
        tweet1.setContent("Merhaba Twitter!");
        tweet2 = new Tweet();
        tweet2.setContent("İkinci Tweet");

        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);
    }
    @AfterEach
    void tearDown() {
        tweetRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Tweet> tweetList = tweetRepository.findAll();

        assertThat(tweetList).hasSize(2);
        assertThat(tweetList)
                .extracting(Tweet::getContent)//Tweet içerisindeki get contentler
                .contains(
                        "Merhaba Twitter!",
                        "İkinci Tweet"
                );
    }
    @Test
     void findById() {
        Tweet foundTweet = tweetRepository.findById(tweet1.getId()).orElse(null);
        assertNotNull(foundTweet);
        assertThat(foundTweet.getContent()).isEqualTo("Merhaba Twitter!");
    }
    @Test
    void delete() {
        tweetRepository.delete(tweet1);
        Tweet deletedTweet = tweetRepository.findById(tweet1.getId()).orElse(null);
        assertThat(deletedTweet).isNull();
    }
}