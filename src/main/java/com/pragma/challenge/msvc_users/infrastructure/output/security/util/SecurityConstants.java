package com.pragma.challenge.msvc_users.infrastructure.output.security.util;

public class SecurityConstants {
    private SecurityConstants() {
        throw new IllegalStateException("Security Utility class");
    }

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String CLAIM_ROLE = "role";
}