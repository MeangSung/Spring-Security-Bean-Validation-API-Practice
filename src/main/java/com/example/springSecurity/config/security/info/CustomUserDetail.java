package com.example.springSecurity.config.security.info;

import com.example.springSecurity.config.security.domain.type.SecurityRole;
import com.example.springSecurity.user.repo.entity.User;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {

    private final String email;
    private final String password;
    private final SecurityRole role;

    @Getter
    private final String nickname;

    public static CustomUserDetail create(User user){
        return CustomUserDetail.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getSecurityRole())
                .build();
    };

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
