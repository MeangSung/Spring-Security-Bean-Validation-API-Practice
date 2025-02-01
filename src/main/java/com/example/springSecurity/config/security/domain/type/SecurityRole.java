package com.example.springSecurity.config.security.domain.type;

import com.example.springSecurity.common.exception.CommonException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SecurityRole {

    OWNER("관리자", "OWNER", "ROLE_OWNER"),
    USER("사용자", "USER", "ROLE_USER");

    private final String koName;
    private final String enName;
    private final String securityName;

    public static SecurityRole fromString(String role) {
        return switch(role.toUpperCase()){
            case "OWNER" -> OWNER;
            case "USER" -> USER;
            default -> throw new IllegalArgumentException("Security Role이 잘못 되었습니다.");
        };
    }
}
