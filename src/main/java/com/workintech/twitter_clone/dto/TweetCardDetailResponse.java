package com.workintech.twitter_clone.dto;

import java.time.LocalDate;
import java.util.List;

public record TweetCardDetailResponse (
        long id,
        String content,
        LocalDate tweetTime,
        long userId,
        String userName,
        long likeCount,
        long commentCount,
        long retweetCount,
        boolean likedByCurrentUser,
        Long currentUserRetweetId,
        List<CommentResponse> comments,
        List<Long> likedUserIds,
        List<Long> retweetUserIds
){
}
