package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.CommentRequest;
import com.workintech.twitter_clone.entity.Comment;

public interface CommentService {
    Comment findById(long id);
    Comment save(CommentRequest commentRequest);
    Comment update(Comment comment);
    void delete(Comment comment);
}
