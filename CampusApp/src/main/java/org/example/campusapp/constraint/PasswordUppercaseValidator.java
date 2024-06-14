package org.example.campusapp.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordUppercaseValidator implements ConstraintValidator<PasswordWithUppercase, String> {

    @Override
    public void initialize(PasswordWithUppercase password) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s == null){
            return true;
        }
        return s.matches(".*[A-Z].*");

    }
}