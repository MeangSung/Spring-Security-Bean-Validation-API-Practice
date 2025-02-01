package com.example.springSecurity.auth.app.ui;

import com.example.springSecurity.auth.app.AuthService;
import com.example.springSecurity.auth.app.dto.request.LoginRequestDto;
import com.example.springSecurity.auth.app.dto.TokenDto;
import com.example.springSecurity.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseDto<TokenDto> login(
            @RequestBody
            @Validated
            LoginRequestDto dto){
        return ResponseDto.ok(authService.login(dto));
    }

}
