package com.workintech.twitter_clone.util;

import com.workintech.twitter_clone.dto.*;
import com.workintech.twitter_clone.entity.Comment;
import com.workintech.twitter_clone.entity.Like;
import com.workintech.twitter_clone.entity.Retweet;
import com.workintech.twitter_clone.entity.Tweet;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static List<TweetResponse> tweetResponseConvert(List<Tweet> tweetList) {
        List<TweetResponse> tweetResponseList = new ArrayList<>();
        for(Tweet tweet: tweetList) {
            tweetResponseList.add(tweetResponseConvert(tweet));
        }
        return tweetResponseList;
    }
    public static TweetResponse tweetResponseConvert(Tweet tweet) {
        return new TweetResponse(tweet.getId(), tweet.getContent(), tweet.getTweetTime(), tweet.getUser().getId());
    }
    public static TweetDetailResponse tweetDetailResponse(Tweet tweet) {
        List<CommentResponse> comments = new ArrayList<>();
        List<Long> likedUserIds = tweet.getLikes()
                .stream()
                .map(like -> like.getUser().getId())
                .toList();
        List<Long> retweetUserIds = tweet.getRetweets()
                .stream()
                .map(retweet -> retweet.getUser().getId())
                .toList();
        for (Comment comment : tweet.getComments()) {
            comments.add(new CommentResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getCommentTime(),
                    comment.getTweet().getId(),
                    comment.getUser().getId()
            ));
        }

        return new TweetDetailResponse(
                tweet.getId(),
                tweet.getContent(),
                tweet.getTweetTime(),
                tweet.getUser().getId(),
                comments,
                tweet.getLikes().size(),
                likedUserIds,
                retweetUserIds.size(),
                retweetUserIds
        );
    }
    public static CommentResponse commentResponseConvert(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getContent(), comment.getCommentTime(), comment.getTweet().getId(),
                comment.getUser().getId());
    }
    public static LikeResponse likeResponseConvert(Like like) {
        return new LikeResponse(like.getId(),like.getTweet().getId(), like.getUser().getId());
    }

    public static RetweetResponse retweetResponseConvert(Retweet retweet) {
        return new RetweetResponse(retweet.getId(),retweet.getTweet().getId(), retweet.getUser().getId());
    }
}
