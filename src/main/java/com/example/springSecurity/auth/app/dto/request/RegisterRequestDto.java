package com.example.springSecurity.auth.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

        @NotBlank
        String username,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        String nickname
) {
}
