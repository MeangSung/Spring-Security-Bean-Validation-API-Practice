package com.example.springSecurity.config.security.app.dto.response;

import com.example.springSecurity.common.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class DefaultJsonWebTokenDto extends SelfValidating<DefaultJsonWebTokenDto> {

    @JsonProperty("access_token")
    @NotBlank
    private final String accessToken;

    @JsonProperty("refresh_token")
    @NotBlank
    private final String refreshToken;

    public DefaultJsonWebTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.validateSelf();
    }
}
