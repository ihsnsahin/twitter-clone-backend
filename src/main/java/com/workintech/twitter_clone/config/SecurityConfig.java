package com.workintech.twitter_clone.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf->
                        csrf.disable())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/register/**").permitAll();
                    auth.requestMatchers("/login/**").permitAll();
                    auth.requestMatchers("/logout").permitAll();

                    auth.requestMatchers(HttpMethod.POST, "/tweet/**")
                            .hasAuthority("USER");

                    auth.requestMatchers(HttpMethod.DELETE, "/tweet/**")
                            .hasAuthority("USER");
                    auth.anyRequest().authenticated();
                })
                //.formLogin(Customizer.withDefaults())// Kendi login endpointimiz, oluşturduğumuz için siliyoruz.
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Yönlendirme (Redirect) YAPMA, sadece HTTP 200 OK dön!
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .invalidateHttpSession(true) // Oturumu temizler
                        .clearAuthentication(true)  // Auth bilgisini siler
                        .deleteCookies("JSESSIONID") // Varsa session cookie'yi siler
                        .permitAll()
                )//logout mekanizması için ekledik.
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authmanager (UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173",
                        "http://localhost:3200",
                        "https://twitter-clone-chi-lime.vercel.app"
                ));

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);//yeni ekledim.//withCredentials: true yapmak için;

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    @Bean//Login için eklendi.
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}
