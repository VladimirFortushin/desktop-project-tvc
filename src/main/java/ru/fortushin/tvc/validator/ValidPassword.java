package ru.fortushin.tvc.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "Password must contain at least one digit [0-9].\n" +
            "    Password must contain at least one lowercase Latin character [a-z].\n" +
            "    Password must contain at least one uppercase Latin character [A-Z].\n" +
            "    Password must contain at least one special character like ! @ # & ( ).\n" +
            "    Password must contain a length of at least 8 characters and a maximum of 20 characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
