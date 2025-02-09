package com.example.springSecurity.common.constant;

import java.util.List;

public class Constants {

    //JWT
    public static String ACCOUNT_ID_ATTRIBUTE_NAME = "email";
    public static String ACCOUNT_ROLE_CLAIM_NAME = "rol";

    //Header
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String BEARER_PREFIX = "Bearer ";

    /**
     * 인증이 필요 없는 url
     */
    public static List<String> NO_NEED_AUTH_URLS = List.of(
            // Authentication/Authorization
            "/login",
            "/api/auth/register",
            "/api/auth/login",
            "/api/auth/validate/**",
            // Swagger
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**",
            //OAuth
            "/login/oauth2/code/kakao",
            "/api/auth/oauth2/authorize",
            "/oauth2/authorization/kakao",

            "/test-osrm"
    );

    public static final List<String> USER_URLS = List.of(
    "/api/**"
            );
}
