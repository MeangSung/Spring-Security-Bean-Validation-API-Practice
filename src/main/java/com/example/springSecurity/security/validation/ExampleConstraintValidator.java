package com.example.springSecurity.security.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExampleConstraintValidator implements ConstraintValidator<ExampleConstraint, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //검증 로직
        return s != null;
    }
}
