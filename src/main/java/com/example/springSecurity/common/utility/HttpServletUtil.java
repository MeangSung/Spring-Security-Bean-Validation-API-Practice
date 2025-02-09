package com.example.springSecurity.common.utility;

import com.example.springSecurity.security.app.dto.DefaultJsonWebTokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpServletUtil {

    private final ObjectMapper objectMapper;

    public void onSuccessBodyResponseWithJwtBody(
            HttpServletResponse response, DefaultJsonWebTokenDto token) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.CREATED.value());

        Map<String, Object> result = new HashMap<>();

        result.put("success", true);
        result.put("data", Map.of(
                "access_token", token.getAccessToken()
        ));
        result.put("error", null);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
