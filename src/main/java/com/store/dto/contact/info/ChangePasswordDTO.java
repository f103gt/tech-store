package com.store.dto.contact.info;

import com.store.validation.password.PasswordConfirmation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordConfirmation
public class ChangePasswordDTO {
    //TODO check accuracy of a password
    @NotBlank(message = "Current password is required.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,20}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character (@$!%*?&), and be 8-20 characters long.")
    private String currentPassword;
    @NotBlank(message = "New password is required.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,20}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character (@$!%*?&), and be 8-20 characters long.")
    private String newPassword;

    @NotBlank
    protected String passwordConfirmation;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
