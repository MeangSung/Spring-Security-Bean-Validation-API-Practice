package com.example.springSecurity.common.dto;

import com.example.springSecurity.common.exception.ErrorCode;
import com.example.springSecurity.security.app.dto.ExceptionDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public class ArgumentNotValidExceptionDto extends ExceptionDto {

  private final Map<String, String> errorFields;

  public ArgumentNotValidExceptionDto(
      final MethodArgumentNotValidException methodArgumentNotValidException) {
    super(ErrorCode.INVALID_ARGUMENT.getCode(),ErrorCode.INVALID_ARGUMENT.getMessage());

    this.errorFields = new HashMap<>();
    methodArgumentNotValidException
        .getBindingResult()
        .getAllErrors()
        .forEach(e -> this.errorFields.put(((FieldError) e).getField(), e.getDefaultMessage()));
  }

  public ArgumentNotValidExceptionDto(
      final ConstraintViolationException constraintViolationException) {
    super(ErrorCode.INVALID_ARGUMENT.getCode(), ErrorCode.INVALID_ARGUMENT.getMessage());

    this.errorFields = new HashMap<>();

    for (ConstraintViolation<?> constraintViolation :
        constraintViolationException.getConstraintViolations()) {
      errorFields.put(
          constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
    }
  }
}
