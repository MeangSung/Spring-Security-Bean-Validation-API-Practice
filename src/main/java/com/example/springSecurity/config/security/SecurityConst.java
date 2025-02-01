package com.example.springSecurity.config.security;

import java.util.List;

public class SecurityConst {

    private SecurityConst() {}

    public static final List<String> ALLOW_URLS
        = List.of("/api", "/api/register", "/api/login");

    public static final List<String> USER_ROLES
        = List.of("USER", "OWNER");
}
