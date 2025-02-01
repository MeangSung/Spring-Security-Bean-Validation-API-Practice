package com.example.springSecurity.auth.app;

import com.example.springSecurity.auth.app.dto.TokenDto;
import com.example.springSecurity.auth.app.dto.request.LoginRequestDto;
import com.example.springSecurity.config.security.jwt.JwtTokenGenerator;
import com.example.springSecurity.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;

    public TokenDto login(LoginRequestDto dto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

    }
}
