package com.workintech.twitter_clone.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(TwitterException.class)
    public ResponseEntity<ExceptionResponse> handleException (TwitterException twitterException) {
        ExceptionResponse errorResponse
                = new ExceptionResponse(
                twitterException.getMessage(),
                twitterException.getHttpStatus().value(),
                LocalDateTime.now());
        log.error("Exception occurred", twitterException);
        return new ResponseEntity<>(errorResponse, twitterException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException (Exception exception) {
        ExceptionResponse errorResponse
                = new ExceptionResponse(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
