package com.store.service.web.dto.registration;

import com.store.service.web.validation.email.annotation.EmailExists;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserEmailRegistrationDto {
    @NotBlank(message = "Email is required.")
    @Email(regexp = "^[a-z0-9]+(?:[.+\\-][a-z0-9]+)*@gmail\\.com$", message = "Please provide a valid Gmail address.")
    @EmailExists
    protected String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
