package com.store.dto.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserLoginDTO {
    @NotBlank(message = "Email is required.")
    @Email(regexp = "^[a-z0-9]+(?:[.+\\-][a-z0-9]+)*@gmail\\.com$", message = "Please provide a valid Gmail address.")
    protected String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,20}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character (@$!%*?&), and be 8-20 characters long.")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
