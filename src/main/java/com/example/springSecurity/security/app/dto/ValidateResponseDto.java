package com.example.springSecurity.security.app.dto;

public record ValidateResponseDto(Boolean valid) {
    public static ValidateResponseDto from(Boolean success) {
        return new ValidateResponseDto(success);
    }
}
