package org.example.campusapp.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StartGValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartG{
    String message() default "Group name must start with G";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}