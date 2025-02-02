package com.example.springSecurity.security.app.dto;

import com.example.springSecurity.common.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DefaultJsonWebTokenDto extends SelfValidating<DefaultJsonWebTokenDto> {

    @JsonProperty("access_token")
    @NotBlank
    private final String accessToken;

    public DefaultJsonWebTokenDto(String accessToken) {
        this.accessToken = accessToken;
        this.validateSelf();
    }
}
