package com.example.springSecurity.auth.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto (
    @NotBlank
    String email,

    @Size(min = 8)
    @NotBlank
    String password
    ){

}
