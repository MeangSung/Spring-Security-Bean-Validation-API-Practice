package com.example.springSecurity.common.constant;

import java.util.List;

public class Constants {

    //JWT
    public static String ACCOUNT_ID_ATTRIBUTE_NAME = "email";
    public static String ACCOUNT_ROLE_CLAIM_NAME = "rol";

    /**
     * 인증이 필요 없는 url
     */
    public static List<String> NO_NEED_AUTH_URLS = List.of(
            // Authentication/Authorization
            "/auth/validations/**",
            "/auth/reissue/token",
            "/auth/reissue/authentication-code",
            "/auth/sign-up",
            "/auth/login",
            "/auth/users",
            "/auth/owners",

            // Term
            "/terms/**",

            // Guest
            "/guests/**",

            // Swagger
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**",

            "/test-osrm"
    );
}
