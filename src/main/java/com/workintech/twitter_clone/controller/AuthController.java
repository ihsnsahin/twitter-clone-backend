package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.dto.*;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exceptions.TwitterException;
import com.workintech.twitter_clone.service.AuthenticationService;
import com.workintech.twitter_clone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthController {
    //Register
    private AuthenticationService authenticationService;
    private UserService userService;//Me endpoint için eklendi.
    private AuthenticationManager authenticationManager;//Login için eklendi.
    private SecurityContextRepository securityContextRepository;//Login için eklendi.
    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        authenticationService.register(registerRequest.userName(), registerRequest.email(), registerRequest.password());
        return new RegisterResponse(registerRequest.userName(), "Kayıt başarılı bir şekilde gerçekleşti.");
    }
    // Login
    @PostMapping("/login")// Derste anlatılmadı.
    // AI yardımı ile yaptım ama mantığını anladım.
    // JWT karmaşık geldiği için bunu yaptım. Session Id üzerinden yaptım.
    public LoginResponse login(  @RequestBody LoginRequest request,
                                 HttpServletRequest httpRequest,
                                 HttpServletResponse httpResponse) {
        // Gelen istekteki credentials bilgilerine göre authenticatation kontrolü yapıyoruz.
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.email(),
                                    request.password()
                            )
                    );
            SecurityContext context = SecurityContextHolder.createEmptyContext(); // Yeni context oluşturuldu
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, httpRequest, httpResponse);

            return new LoginResponse("Giriş başarı ile gerçekleşti.");

        } catch (BadCredentialsException e) {//Başarıız olursa bu hatayı fırlatacak yakalayıp  kendi excepitonımızı döndürdük.
            throw new TwitterException(
                    "E-posta veya şifre hatalı.",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        User currentUser = userService.findByEmail(authentication.getName());
        return new UserResponse(currentUser.getId(), currentUser.getName());
    }
}
