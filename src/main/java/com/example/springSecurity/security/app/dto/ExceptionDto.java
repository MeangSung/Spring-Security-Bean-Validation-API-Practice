package com.example.springSecurity.security.app.dto;

import com.example.springSecurity.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionDto {

    @JsonProperty("code")
    private final Integer code;

    @JsonProperty("message")
    private final String message;

    public ExceptionDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ExceptionDto of(ErrorCode errorCode, String message) {
        return new ExceptionDto(errorCode.getCode(),message);
    }

    public static ExceptionDto of(ErrorCode errorCode){
        return new ExceptionDto(errorCode.getCode(), errorCode.getMessage());
    }
}
