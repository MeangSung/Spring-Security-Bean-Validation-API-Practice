package com.example.springSecurity.security.info;

import com.example.springSecurity.common.exception.CommonException;
import com.example.springSecurity.common.exception.ErrorCode;
import com.example.springSecurity.user.repo.entity.User;
import com.nimbusds.jose.util.Base64;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuth2UserInfo {

    private final String name;
    private final String email;

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(attributes);
        }
        throw new CommonException(ErrorCode.ILLEGAL_REGISTRATION_ID);
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2UserInfo.builder()
                .email((String) kakaoAccount.get("email"))
                .name((String) profile.get("nickname"))
                .build();
    }

    public User toEntity() {
        String password = Base64.encode("kakao_" + email + name).toString();
        return User.createUser(email, password, name);
    }
}
