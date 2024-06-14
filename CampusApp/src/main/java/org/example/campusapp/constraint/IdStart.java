package org.example.campusapp.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IdStartValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdStart {
    String message() default "Id of the user must start with 's' for a student, 't' for a teacher";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}