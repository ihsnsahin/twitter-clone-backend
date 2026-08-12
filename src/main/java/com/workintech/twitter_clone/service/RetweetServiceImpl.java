package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.RetweetRequest;
import com.workintech.twitter_clone.entity.Retweet;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exceptions.TwitterException;
import com.workintech.twitter_clone.repository.RetweetRepository;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RetweetServiceImpl implements  RetweetService{
    private final RetweetRepository retweetRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    @Override
    public Retweet findById(long id) {
        return retweetRepository.findById(id).orElseThrow(()->
                new TwitterException("Retweet bulunamadı", HttpStatus.NOT_FOUND)
                );
    }
    @Override
    public Retweet save(RetweetRequest retweetRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                ()-> new TwitterException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND)
        );
        Tweet tweet = tweetRepository.findById(retweetRequest.tweetId()).orElseThrow( ()->
                new TwitterException("Tweet bulunamadı", HttpStatus.NOT_FOUND)
        );
        if (retweetRepository.findByUserIdAndTweetId(user.getId(), tweet.getId()).isPresent()) {
            throw new TwitterException(
                    "Retweet yapılmış. Tekrar retweet yapılamaz.",
                    HttpStatus.BAD_REQUEST
            );
        }

        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        return retweetRepository.save(retweet);
    }
    @Override
    public void delete(Retweet retweet) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isRetweetOwner =
                retweet.getUser().getEmail().equals(authentication.getName());
        if (!isRetweetOwner) {
            throw new TwitterException(
                    "Retweet'i yalnızca sahibi silebilir.",
                    HttpStatus.FORBIDDEN
            );
        }
        retweetRepository.delete(retweet);
    }

}
