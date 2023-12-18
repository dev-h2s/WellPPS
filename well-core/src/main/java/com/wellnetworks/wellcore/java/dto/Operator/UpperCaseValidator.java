package com.wellnetworks.wellcore.java.dto.Operator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpperCaseValidator implements ConstraintValidator<UpperCase, String> {

    @Override
    public void initialize(UpperCase constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.equals(value.toUpperCase());
    }
}
