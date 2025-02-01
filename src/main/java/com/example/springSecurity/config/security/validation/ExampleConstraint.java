package com.example.springSecurity.config.security.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintTarget;
import jakarta.validation.Payload;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {ExampleConstraintValidator.class})
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT) //ConstaintValidator 에서 쓰일 값 고정
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExampleConstraint {

    //필수 요소
    String message() default "{com.example.springSecurity.config.security.validation.ExampleConstraint.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    //추가 요소
    ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;

    String test() default "test";
}
