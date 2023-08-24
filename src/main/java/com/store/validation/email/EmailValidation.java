package com.store.validation.email;

public interface EmailValidation {

    void sendEmail(String message,String email,String token);
    void otpEmailValidation(String message, String email,String otp);
}
