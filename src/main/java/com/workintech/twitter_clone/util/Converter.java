package com.workintech.twitter_clone.util;

import com.workintech.twitter_clone.dto.*;
import com.workintech.twitter_clone.entity.*;

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
        long likeCount = tweet.getLikes() == null
                ? 0
                : tweet.getLikes().size();

        long commentCount = tweet.getComments() == null
                ? 0
                : tweet.getComments().size();

        long retweetCount = tweet.getRetweets() == null
                ? 0
                : tweet.getRetweets().size();
        return new TweetResponse(
                tweet.getId(),
                tweet.getContent(),
                tweet.getTweetTime(),
                tweet.getUser().getId(),
                tweet.getUser().getName(),
                likeCount,
                commentCount,
                retweetCount
        );
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
                    new UserResponse(comment.getUser().getId(), comment.getUser().getName())
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
                new UserResponse(comment.getUser().getId(), comment.getUser().getName()));
    }
    public static LikeResponse likeResponseConvert(Like like) {
        return new LikeResponse(like.getId(),like.getTweet().getId(), like.getUser().getId());
    }

    public static RetweetResponse retweetResponseConvert(Retweet retweet, User currentUser) {
        return new RetweetResponse(
                retweet.getId(),
                new UserResponse(retweet.getUser().getId(), retweet.getUser().getName()),
                tweetCardResponseConvert(retweet.getTweet(), currentUser)
        );
    }

    public static List<RetweetResponse> retweetResponseConvert(List<Retweet> retweetList, User currentUser) {
        List<RetweetResponse> retweetResponseList = new ArrayList<>();
        for(Retweet retweet: retweetList) {
            retweetResponseList.add(
                    Converter.retweetResponseConvert(retweet, currentUser)
            );
        }
        return retweetResponseList;
    }

    //Tweet Card ile ilgili hazırlamak zorunda kaldığım converterlar.


    public static List<TweetCardResponse> tweetCardResponseConvert(
            List<Tweet> tweetList,
            User currentUser) {

        List<TweetCardResponse> tweetCardResponseList = new ArrayList<>();

        for (Tweet tweet : tweetList) {
            tweetCardResponseList.add(
                    tweetCardResponseConvert(tweet, currentUser)
            );
        }

        return tweetCardResponseList;
    }
    public static TweetCardResponse tweetCardResponseConvert(Tweet tweet, User currentUser) {
        long likeCount = tweet.getLikes() == null
                ? 0
                : tweet.getLikes().size();

        long commentCount = tweet.getComments() == null
                ? 0
                : tweet.getComments().size();

        long retweetCount = tweet.getRetweets() == null
                ? 0
                : tweet.getRetweets().size();
        //Tweet Card için ekliyoruz.
        Long currentUserRetweetId = null;
        if (tweet.getRetweets() != null) {

            for (Retweet retweet : tweet.getRetweets()) {

                if (retweet.getUser().getId() == currentUser.getId()) {
                    currentUserRetweetId = retweet.getId();
                    break;
                }
            }
        }
        boolean likedByCurrentUser = false;

        if (tweet.getLikes() != null) {

            for (Like like : tweet.getLikes()) {

                if (like.getUser().getId() == currentUser.getId()) {
                    likedByCurrentUser = true;
                    break;
                }
            }
        }
        return new TweetCardResponse(
                tweet.getId(),
                tweet.getContent(),
                tweet.getTweetTime(),
                tweet.getUser().getId(),
                tweet.getUser().getName(),
                likeCount,
                commentCount,
                retweetCount,
                likedByCurrentUser,
                currentUserRetweetId


        );
    }
    public static TweetCardDetailResponse tweetCardDetailResponse(Tweet tweet, User currentUser) {
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
                    new UserResponse(comment.getUser().getId(), comment.getUser().getName())
            ));
        }
        long likeCount = tweet.getLikes() == null
                ? 0
                : tweet.getLikes().size();

        long commentCount = tweet.getComments() == null
                ? 0
                : tweet.getComments().size();

        long retweetCount = tweet.getRetweets() == null
                ? 0
                : tweet.getRetweets().size();
        //Tweet Card için ekliyoruz.

        Long currentUserRetweetId = null;
        if (tweet.getRetweets() != null) {

            for (Retweet retweet : tweet.getRetweets()) {

                if (retweet.getUser().getId() == currentUser.getId()) {
                    currentUserRetweetId = retweet.getId();
                    break;
                }
            }
        }
        boolean likedByCurrentUser = false;

        if (tweet.getLikes() != null) {

            for (Like like : tweet.getLikes()) {

                if (like.getUser().getId() == currentUser.getId()) {
                    likedByCurrentUser = true;
                    break;
                }
            }
        }
        return new TweetCardDetailResponse(
                tweet.getId(),
                tweet.getContent(),
                tweet.getTweetTime(),
                tweet.getUser().getId(),
                tweet.getUser().getName(),
                likeCount,
                commentCount,
                retweetCount,
                likedByCurrentUser,
                currentUserRetweetId,
                comments,
                likedUserIds,
                retweetUserIds
        );
    }

}
