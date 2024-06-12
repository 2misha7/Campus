package org.example.url.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordLowercaseValidator implements ConstraintValidator<PassswordWithLowercase, String> {

    @Override
    public void initialize(PassswordWithLowercase passswordWithLowercase) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s == null){
            return true;
        }
        return s.matches(".*[a-z].*");

    }
}
