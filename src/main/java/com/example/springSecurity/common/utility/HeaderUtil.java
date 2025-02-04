package com.example.springSecurity.common.utility;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 헤더에 있는 데이터를 다루는 util class
 */
@Component
public class HeaderUtil {

    public static Optional<String> refineHeader(HttpServletRequest request, String header, String prefix) {
        String unpreparedToken = request.getHeader(header);

        if(!StringUtils.hasText(unpreparedToken) || !unpreparedToken.startsWith(prefix)) {
            return Optional.empty();
        }

        return Optional.of(unpreparedToken.substring(prefix.length()));
    }
}
