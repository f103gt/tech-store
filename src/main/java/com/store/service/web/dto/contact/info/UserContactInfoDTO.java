package com.store.service.web.dto.contact.info;

import com.store.dao.model.users.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public class UserContactInfoDTO implements Serializable {

    /*@NotBlank(message = "First name is required.")*/
    @Pattern(regexp = "^[A-Z][a-z]{1,49}$", message = "First name must start with a capital letter and contain only alphabets (2-50 characters).")
    protected String firstName;

    /*@NotBlank(message = "Last name is required.")*/
    @Pattern(regexp = "^[A-Z][a-z]{1,49}$", message = "Last name must start with a capital letter and contain only alphabets (2-50 characters).")
    protected String lastName;

    /*@NotBlank(message = "Patronymic is required.")*/
    @Pattern(regexp = "^[A-Z][a-z]{1,49}$", message = "Patronymic must start with a capital letter and contain only alphabets (2-50 characters).")
    private String patronymic;

    /*@NotBlank(message = "Email is required.")*/
    @Email(regexp = "^[a-z0-9]+(?:[.+\\-][a-z0-9]+)*@gmail\\.com$", message = "Please provide a valid Gmail address.")
    protected String email;

    /*@NotBlank(message = "Phone number is required.")*/
    @Pattern(regexp = "^0\\d{2}\\s?\\d{3}\\s?\\d{4}$", message = "Phone number must contain 10 digits and start with a number of a mobile service provider.")
    private String phone;

    public UserContactInfoDTO(){}

    public UserContactInfoDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.email = user.getEmail();
        this.phone = user.getPhone();
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
