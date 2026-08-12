package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.entity.Role;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exceptions.TwitterException;
import com.workintech.twitter_clone.repository.RoleRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User register(String userName, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new TwitterException("Verilen email adresi ile kullanıcı mevcut.", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.findByUserName(userName).isPresent()) {
            throw new TwitterException("Verilen kullanıcı adı ile kullanıcı mevcut.", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(password);

        List<Role> roles = new ArrayList<>();
        Optional<Role> roleUser = roleRepository.findByAuthority("USER");
        if(!roleUser.isPresent()) {
           Role roleUserEntity = new Role();
           roleUserEntity.setAuthority("USER");
            roles.add(roleUserEntity);
            roleRepository.save(roleUserEntity);
        } else {
            roles.add(roleUser.get());
        }

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
