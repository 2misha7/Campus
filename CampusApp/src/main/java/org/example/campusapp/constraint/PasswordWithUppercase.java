package org.example.campusapp.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordUppercaseValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordWithUppercase {
    String message() default "{org.example.url.password.uppercase}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
