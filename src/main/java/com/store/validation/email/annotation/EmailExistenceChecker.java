package com.store.validation.email.annotation;

import com.store.config.security.model.User;
import com.store.repository.users.UserDao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailExistenceChecker implements ConstraintValidator<EmailExists, Object> {
    private final UserDao userDAO;

    @Autowired
    public EmailExistenceChecker(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        String email = (String) object;
        User user = userDAO.getUserByParameter("email",email);
        return user == null;
    }

    @Override
    public void initialize(EmailExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
