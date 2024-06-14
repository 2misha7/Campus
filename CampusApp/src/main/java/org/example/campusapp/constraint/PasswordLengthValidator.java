package org.example.campusapp.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordLengthValidator implements ConstraintValidator<PasswordLength, String> {

    @Override
    public void initialize(PasswordLength passwordLength) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s == null){
            return true;
        }
        return s.length() > 10;
    }
}
