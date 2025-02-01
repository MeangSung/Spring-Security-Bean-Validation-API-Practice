package com.example.springSecurity.common.dto;

import com.example.springSecurity.common.exception.CommonException;
import com.example.springSecurity.common.exception.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;


/**
 * SelfValidating을 상속받아 만들어지는 클래스는
 * 해당 클래스가 생성될 때 Validation 을 실시한다.
 * @param <T>
 */
@Slf4j
public abstract class SelfValidating<T> {

    private final Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this); // 전체 유효성 검증 수행 로직
        if(!violations.isEmpty()) {
            log.error("Validation error occured: {}", violations);
            throw new CommonException(ErrorCode.INTERNAL_DATA_ERROR);
        }
    }
}
