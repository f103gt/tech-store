package com.store.validation.email;

import com.store.validation.token.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com.store.validation.token")
public class EmailValidationImpl implements EmailValidation {
    private final JavaMailSender javaMailSender;
    private final VerificationToken tokenBuilder;

    @Autowired
    public EmailValidationImpl(JavaMailSender javaMailSender, VerificationToken tokenBuilder) {
        this.javaMailSender = javaMailSender;
        this.tokenBuilder = tokenBuilder;
    }

    @Override
    @Async
    public void sendEmail(String message,String email,String token) {
        String validationUrl = createValidationUrl(token);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Email Validation");
        mailMessage.setText(message + "\n" + validationUrl);
        javaMailSender.send(mailMessage);
    }

    @Override
    @Async
    public void otpEmailValidation(String message, String email, String otp) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Email Validation");
        mailMessage.setText(message + "\n" + otp);
        javaMailSender.send(mailMessage);
    }

    private String createValidationUrl(String token) {
        return "http://localhost:8080/tech-store.com/email-confirmation?token=" + token;
    }
}
