package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exceptions.TwitterException;
import com.workintech.twitter_clone.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void findAll() {
        userService.findAll();
        verify(userRepository).findAll();
    }

    @Test
    void findById() {
        User user = new User();
        user.setUserName("ihsan");
        user.setEmail("ihsan@test.com");
        user.setPassword("123456");
        //Stubbing
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        User foundUser = userService.findById(user.getId());
        verify(userRepository).findById(user.getId());
    }
    @Test
    void cannotFindById() {
        assertThatThrownBy(()-> userService.findById(2L))
                .isInstanceOf(TwitterException.class);
        verify(userRepository).findById(2L);
    }

    @Test
    void findByEmail() {
        User user = new User();
        user.setId(1L);
        user.setUserName("ihsan");
        user.setEmail("ihsan@test.com");
        user.setPassword("123456");
        given(userRepository.findByEmail("ihsan@test.com")).willReturn(Optional.of(user));
        User foundUser = userService.findByEmail("ihsan@test.com");
        verify(userRepository).findByEmail("ihsan@test.com");
    }

    @Test
    void save() {
        User user = new User();
        user.setUserName("ihsan");
        user.setEmail("ihsan@test.com");
        user.setPassword("123456");
        userService.save(user);
        verify(userRepository).save(user);
    }

    @Test
    void delete() {
        User user = new User();
        user.setUserName("ihsan");
        user.setEmail("ihsan@test.com");
        user.setPassword("123456");
        userService.delete(user);
        verify(userRepository).delete(user);
    }

}