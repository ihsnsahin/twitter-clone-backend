package com.workintech.twitter_clone.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Kullanıcı adı boş olamaz")
        @Size(min = 3, max = 30, message = "Kullanıcı adı 3-30 karakter arasında olmalıdır")
        String userName,
        @NotBlank(message = "Email boş olamaz")
        @Email(message = "Geçerli bir email adresi giriniz")
        String email,
        @NotBlank(message = "Şifre boş olamaz")
        @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
        String password) {
}
