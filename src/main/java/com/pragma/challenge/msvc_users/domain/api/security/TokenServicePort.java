package com.pragma.challenge.msvc_users.domain.api.security;

public interface TokenServicePort {
    boolean validateToken(String token, Long username);
    Long getUsername(String token);
}
