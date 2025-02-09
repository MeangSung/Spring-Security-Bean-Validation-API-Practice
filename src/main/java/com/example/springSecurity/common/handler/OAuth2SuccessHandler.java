package com.example.springSecurity.common.handler;

import com.example.springSecurity.common.utility.HttpServletUtil;
import com.example.springSecurity.common.utility.JsonWebTokenUtil;
import com.example.springSecurity.security.app.dto.DefaultJsonWebTokenDto;
import com.example.springSecurity.security.info.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JsonWebTokenUtil jsonWebTokenUtil;
    private final HttpServletUtil httpServletUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        //accessToken 발급
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        DefaultJsonWebTokenDto jwtWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokenDto(
                principalDetails.getEmail(), principalDetails.getRole());

        httpServletUtil.onSuccessBodyResponseWithJwtBody(response,jwtWebTokenDto);
    }
}
