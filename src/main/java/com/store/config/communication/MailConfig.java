package com.store.config.communication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.store")
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("projectemail430@gmail.com");
        mailSender.setPassword("rezbnznmoawkehar");
        mailSender.setPort(587);

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.starttls.enable", true);
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }
}













