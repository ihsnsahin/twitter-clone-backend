package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.TweetRequest;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exceptions.TwitterException;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class TweetServiceImpl implements TweetService{
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet findById(long id) {
        return tweetRepository.findById(id).orElseThrow(()-> new TwitterException("Tweet bulunamadı" + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Tweet save(TweetRequest tweetRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                ()-> new TwitterException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND)
        );
        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.content());
        tweet.setUser(user);
        tweet.setTweetTime(LocalDate.now());
        return tweetRepository.save(tweet);
    }
    @Override
    public Tweet update(Tweet tweet) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!tweet.getUser().getEmail().equals(authentication.getName())) {
            throw new TwitterException(
                    "Tweeti yalnızca kendi yazarı güncelleyebilir.",
                    HttpStatus.FORBIDDEN
            );
        }

        return tweetRepository.save(tweet);
    }

    @Override
    public void delete(Tweet tweet) {
        //Tweet sahibi mi? diye kontrol ettik. Bussines Logic işlem olduğu için servis katmanı yeterli oldu.
        //SecurityContextHolder -> Güvenlik bilgisinin tutulduğu merkezi yapı
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if(!tweet.getUser().getEmail().equals(authentication.getName())) {
            throw  new TwitterException("Tweeti yalnızca kendi yazarı silebilir.", HttpStatus.FORBIDDEN);
        }
        tweetRepository.delete(tweet);
    }
}
