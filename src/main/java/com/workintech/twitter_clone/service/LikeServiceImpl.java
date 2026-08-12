package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.LikeRequest;
import com.workintech.twitter_clone.entity.Like;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exceptions.TwitterException;
import com.workintech.twitter_clone.repository.LikeRepository;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;


    @Override
    public Like findByUserIdAndTweetId(long userId,long tweetId) {
        return likeRepository.findByUserIdAndTweetId(userId, tweetId).orElseThrow(()->
                new TwitterException("Beğeni bulunamadı", HttpStatus.NOT_FOUND)
                );
    }

    @Override
    public Like save(LikeRequest likeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                ()-> new TwitterException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND)
        );
        Tweet tweet = tweetRepository.findById(likeRequest.tweetId()).orElseThrow( ()->
                new TwitterException("Tweet bulunamadı", HttpStatus.NOT_FOUND)
        );
        if (likeRepository.findByUserIdAndTweetId(user.getId(), tweet.getId()).isPresent()) {
            throw new TwitterException(
                    "Beğeni yapılmış. Tekrar beğeni yapılamaz.",
                    HttpStatus.BAD_REQUEST
            );
        }

        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        return likeRepository.save(like);
    }

    @Override
    public void delete(Like like) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isLikeOwner =
                like.getUser().getEmail().equals(authentication.getName());
        if (!isLikeOwner) {
            throw new TwitterException(
                    "Beğeniyi yalnızca sahibi silebilir.",
                    HttpStatus.FORBIDDEN
            );
        }
        likeRepository.delete(like);
    }
}
