package org.example.campusapp.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DayValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Day{
    String message() default "Not a day of the week";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}