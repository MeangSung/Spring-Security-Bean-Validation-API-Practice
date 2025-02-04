package com.example.springSecurity.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "API 성공 여부")
@Builder
public record CommonSuccessDto(

        @Schema(description = "성공여부")
        boolean isSuccess
) {
    public static CommonSuccessDto fromEntity(boolean success) {
        return CommonSuccessDto.builder().isSuccess(success).build(); }
}
