package com.example.springSecurity.security.info;

import com.example.springSecurity.security.domain.type.SecurityRole;
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

    @Getter private final String email;
    @Getter private final String role;
    private final String password;

    private final String nickname;

    public static CustomUserDetail create(User user){
        return CustomUserDetail.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    };

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
