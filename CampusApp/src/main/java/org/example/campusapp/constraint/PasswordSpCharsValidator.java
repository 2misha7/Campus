package org.example.campusapp.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordSpCharsValidator implements ConstraintValidator<PasswordSpChars, String> {

    @Override
    public void initialize(PasswordSpChars passwordSpChars) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s == null){
            return true;
        }
        return s.matches(".*\\W.*\\W.*");

    }
}