package com.workintech.twitter_clone.repository;

import com.workintech.twitter_clone.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserRepositoryTest {
    private UserRepository userRepository;
    private User user;
    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserName("ihsan");
        user.setEmail("ihsan@test.com");
        user.setPassword("123456");
        userRepository.save(user);
    }
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("Email ile kullanıcı bulunabilir")
    @Test
    void findByEmail() {
        User foundUser = userRepository.findByEmail("ihsan@test.com").orElse(null);
        assertNotNull(foundUser,"Kullanıcı null dönmemeli.");
        assertThat(foundUser.getName()).isEqualTo("ihsan");
        assertThat(foundUser.getPassword()).isEqualTo("123456");
    }
    @DisplayName("Username ile kullanıcı bulunabilir")
    @Test
    void findByUserName() {
        User foundUser = userRepository.findByUserName("ihsan").orElse(null);
        assertNotNull(foundUser, "Kullanıcı null dönmemeli");
        assertThat(foundUser.getEmail()).isEqualTo("ihsan@test.com");
        assertThat(foundUser.getPassword()).isEqualTo("123456");
    }
    @DisplayName("Kullanıcı silindiğinde veritabanından kaldırılmalı")
    @Test
    void delete() {
        userRepository.delete(user);
        User foundUser = userRepository.findByUserName("ihsan").orElse(null);
        assertNull(foundUser);
    }
}