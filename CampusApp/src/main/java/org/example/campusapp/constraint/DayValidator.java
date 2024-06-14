package org.example.campusapp.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class DayValidator implements ConstraintValidator<Day, String> {


    private static final List<String> VALID_DAYS = Arrays.asList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );
    @Override
    public void initialize(Day day) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return VALID_DAYS.contains(value);
    }
}

