package com.example.springSecurity.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public record ResponseDto<T>(
        @JsonIgnore HttpStatus status,
        @Schema(name = "success", description = "api 호출 성공 여부") @NotNull boolean success,
        @Schema(name = "data") @Nullable T data,
        @Schema(name = "error") @Nullable String error

) {
    public static <T> ResponseDto<T> ok(@org.springframework.lang.Nullable final T data) {
        return new ResponseDto<>(HttpStatus.OK, true, data, null);
    }

    public static <T> ResponseDto<T> created(@org.springframework.lang.Nullable final T data) {
        return new ResponseDto<>(HttpStatus.CREATED, true, data, null);
    }
}
