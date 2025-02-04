package com.example.springSecurity.security.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청")
public record RegisterRequestDto(

        @Schema(
            description = "사용자 이메일",
            example = "aaa@aaa.com")
        @Email
        @NotBlank
        String email,

        @Schema(
            description = "비밀번호, 최소 8자리",
            example = "qwer1234!"
        )
        @NotBlank
        @Size(min = 8)
        String password,

        @Schema(
            description = "닉네임",
            example = "닉네임"
        )
        @NotBlank
        String nickname

) {
}
