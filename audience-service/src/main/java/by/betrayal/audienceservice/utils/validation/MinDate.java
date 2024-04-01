package by.betrayal.audienceservice.utils.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface MinDate {
    String message() default "{date less current date}";
}
