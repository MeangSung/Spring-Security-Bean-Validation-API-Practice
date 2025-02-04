package com.example.springSecurity.common.handler;

import com.example.springSecurity.common.utility.HeaderUtil;
import com.example.springSecurity.common.utility.JsonWebTokenUtil;
import com.example.springSecurity.security.app.dto.DefaultJsonWebTokenDto;
import com.example.springSecurity.security.info.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JsonWebTokenUtil jsonWebTokenUtil;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        DefaultJsonWebTokenDto jsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokenDto(
                userDetail.getEmail(),
                userDetail.getRole()
        );


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.CREATED.value());

        Map<String, Object> result = new HashMap<>();

        result.put("success", true);
        result.put("data", Map.of(
                "access_token", jsonWebTokenDto.getAccessToken()
        ));
        result.put("error", null);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
