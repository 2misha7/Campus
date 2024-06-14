package org.example.campusapp.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdStartValidator implements ConstraintValidator<IdStart, String> {

    @Override
    public void initialize(IdStart idStart) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s == null){
            return true;
        }
        return s.matches("^[st].*") || s.equals("admin");
    }
}
