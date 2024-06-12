package org.example.url.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordDigitValidator implements ConstraintValidator<PasswordDigits, String> {

    @Override
    public void initialize(PasswordDigits passwordDigits) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s == null){
            return true;
        }
        return s.matches(".*\\d.*\\d.*\\d.*");
    }
}
