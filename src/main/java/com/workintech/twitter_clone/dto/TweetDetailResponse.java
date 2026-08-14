package com.workintech.twitter_clone.dto;

import java.time.LocalDate;
import java.util.List;

public record TweetDetailResponse(
        long id,
        String content,
        LocalDate tweetTime,
        long userId,
        List<CommentResponse> comments,
        long likeCount,
        List<Long> likedUserIds,
        long retweetCount,
        List<Long> retweetUserIds
) {
}
