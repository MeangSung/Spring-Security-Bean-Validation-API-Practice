package com.example.springSecurity.security.app;

import com.example.springSecurity.common.dto.CommonSuccessDto;
import com.example.springSecurity.security.app.dto.RegisterRequestDto;
import com.example.springSecurity.security.app.dto.ValidateResponseDto;
import com.example.springSecurity.user.repo.UserRepository;
import com.example.springSecurity.user.repo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CommonSuccessDto register(RegisterRequestDto dto) {

        String encodedPassword = passwordEncoder.encode(dto.password());

        User newUser = User.createUser(dto.email(), encodedPassword, dto.nickname());

        userRepository.save(newUser);

        return CommonSuccessDto.fromEntity(true);
    }

    public ValidateResponseDto validateEmail(String email) {
        return userRepository.findByEmail(email).isEmpty() ?
                ValidateResponseDto.from(true) : ValidateResponseDto.from(false);
    }

    public ValidateResponseDto validateNickname(String nickname) {
        return userRepository.findByNickname(nickname).isEmpty() ?
                ValidateResponseDto.from(true) : ValidateResponseDto.from(false);
    }
}
