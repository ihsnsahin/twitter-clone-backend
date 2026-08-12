package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.CommentRequest;
import com.workintech.twitter_clone.dto.CommentResponse;
import com.workintech.twitter_clone.entity.Comment;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exceptions.TwitterException;
import com.workintech.twitter_clone.repository.CommentRepository;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id).orElseThrow(()->
                new TwitterException("Comment bulunamadı" + id, HttpStatus.NOT_FOUND)
                );
    }

    @Override
    public Comment save(CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                ()-> new TwitterException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND)
        );
        Tweet tweet = tweetRepository.findById(commentRequest.tweetId()).orElseThrow( ()->
                       new TwitterException("Tweet bulunamadı", HttpStatus.NOT_FOUND)
        );
        Comment comment = new Comment();
        comment.setContent(commentRequest.content());
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setCommentTime(LocalDate.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        boolean isCommentOwner =
                comment.getUser().getEmail().equals(authentication.getName());

        if (!isCommentOwner) {
            throw new TwitterException(
                    "Commentleri yalnızca kendi yazarı güncelleyebilir.",
                    HttpStatus.FORBIDDEN
            );
        }


        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isCommentOwner =
                comment.getUser().getEmail().equals(authentication.getName());

        boolean isTweetOwner =
                comment.getTweet().getUser().getEmail().equals(authentication.getName());

        if (!isCommentOwner && !isTweetOwner) {
            throw new TwitterException(
                    "Commentleri yalnızca kendi yazarı ya da tweet sahibi silebilir.",
                    HttpStatus.FORBIDDEN
            );
        }

        commentRepository.delete(comment);


    }
}
