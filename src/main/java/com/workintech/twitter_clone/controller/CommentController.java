package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.dto.CommentRequest;
import com.workintech.twitter_clone.dto.CommentResponse;
import com.workintech.twitter_clone.entity.Comment;
import com.workintech.twitter_clone.service.CommentService;
import com.workintech.twitter_clone.util.Converter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/comment")
@Validated
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    CommentResponse save(@Valid @RequestBody CommentRequest commentRequest) {
        Comment savedComment = commentService.save(commentRequest);
        return Converter.commentResponseConvert(savedComment);
    }

    @PutMapping("/{id}")
    CommentResponse update(
            @Positive(message = "Comment id pozitif olmalıdır")
            @PathVariable long id,
            @Valid @RequestBody CommentRequest commentRequest) {

        Comment foundComment = commentService.findById(id);
        foundComment.setContent(commentRequest.content());

        Comment updatedComment = commentService.update(foundComment);

        return Converter.commentResponseConvert(updatedComment);
    }
    @DeleteMapping("/{id}")
    CommentResponse delete(
            @Positive (message = "Comment id pozitif olmalı")
            @PathVariable long id) {
        Comment foundComment = commentService.findById(id);
        commentService.delete(foundComment);
        return Converter.commentResponseConvert(foundComment);
    }
}
