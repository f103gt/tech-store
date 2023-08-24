package com.store.validation.password;

import com.store.dto.registration.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, Object> {
    @Override
    public void initialize(PasswordConfirmation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        UserRegistrationDto user = (UserRegistrationDto) object;
        return user.getPassword().equals(user.getPasswordConfirmation());
    }
}
