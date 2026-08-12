package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User findByEmail(String email);
    User save(User user);
    void delete(User user);
}
