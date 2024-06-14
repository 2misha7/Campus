package org.example.campusapp.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class StartGValidator implements ConstraintValidator<StartG, String> {


    @Override
    public void initialize(StartG startG) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.startsWith("G");
    }
}