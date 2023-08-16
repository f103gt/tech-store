package com.store.service.web.dto.registration;

import com.store.service.web.validation.password.PasswordConfirmation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordConfirmation
public class UserRegistrationDto extends UserEmailRegistrationDto{

    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "^[A-Z][a-z]{1,49}$", message = "First name must start with a capital letter and contain only alphabets (2-50 characters).")
    protected String firstName;

    @NotBlank(message = "Last name is required.")
    @Pattern(regexp = "^[A-Z][a-z]{1,49}$", message = "Last name must start with a capital letter and contain only alphabets (2-50 characters).")
    protected String lastName;

    //TODO check for uniqueness of the password
    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,20}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character (@$!%*?&), and be 8-20 characters long.")
    protected String password;

    @NotBlank
    protected String passwordConfirmation;

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public UserRegistrationDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    @Override
    public String toString() {
        return "CustomerSignUpDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirmation='" + passwordConfirmation + '\'' +
                '}';
    }
}
