package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.RetweetRequest;
import com.workintech.twitter_clone.entity.Retweet;

public interface RetweetService {
    Retweet findById(long id);
    void delete(Retweet retweet);
    Retweet save(RetweetRequest retweetRequest);
}
