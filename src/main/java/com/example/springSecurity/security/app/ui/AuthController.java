package com.example.springSecurity.security.app.ui;

import com.example.springSecurity.common.dto.CommonSuccessDto;
import com.example.springSecurity.common.dto.ResponseDto;
import com.example.springSecurity.security.app.AuthService;
import com.example.springSecurity.security.app.dto.RegisterRequestDto;
import com.example.springSecurity.security.app.dto.ValidateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입을 수행합니다.")
    @SecurityRequirements
    @PostMapping("/register")
    public ResponseDto<CommonSuccessDto> register(
            @RequestBody
            @Validated
            RegisterRequestDto dto) {
        return ResponseDto.created(authService.register(dto));
    }

    @Operation(summary = "이메일 중복확인")
    @GetMapping("/validate/email")
    public ResponseDto<ValidateResponseDto> validateEmail(
            @RequestParam
            @Validated
            @Email
            @NotBlank
            String email) {
        return ResponseDto.ok(authService.validateEmail(email));
    }

    @Operation(summary = "닉네임 중복확인")
    @GetMapping("/validate/nickname")
    public ResponseDto<ValidateResponseDto> validateNickname(
            @RequestParam
            @Validated
            String nickname) {
        return ResponseDto.ok(authService.validateNickname(nickname));
    }
}
