package com.example.springSecurity.common.handler;

import com.example.springSecurity.common.utility.HttpServletUtil;
import com.example.springSecurity.common.utility.JsonWebTokenUtil;
import com.example.springSecurity.security.app.dto.DefaultJsonWebTokenDto;
import com.example.springSecurity.security.info.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JsonWebTokenUtil jsonWebTokenUtil;
    private final HttpServletUtil httpServletUtil;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        DefaultJsonWebTokenDto jsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokenDto(
                userDetail.getEmail(),
                userDetail.getRole()
        );

        httpServletUtil.onSuccessBodyResponseWithJwtBody(response, jsonWebTokenDto);
    }
}
