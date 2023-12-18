package com.wellnetworks.wellcore.java.dto.Operator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UpperCaseValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UpperCase {
    String message() default "The string must be uppercase";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
