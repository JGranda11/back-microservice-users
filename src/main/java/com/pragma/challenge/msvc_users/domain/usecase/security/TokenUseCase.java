package com.pragma.challenge.msvc_users.domain.usecase.security;

import com.pragma.challenge.msvc_users.domain.api.security.TokenServicePort;
import com.pragma.challenge.msvc_users.domain.spi.security.TokenSecurityPort;

public class TokenUseCase implements TokenServicePort {

    private final TokenSecurityPort tokenSecurityPort;

    public TokenUseCase(TokenSecurityPort tokenSecurityPort) {
        this.tokenSecurityPort = tokenSecurityPort;
    }

    @Override
    public boolean validateToken(String token, Long username) {
        return tokenSecurityPort.validateToken(token, username);
    }

    @Override
    public Long getUsername(String token) {
        return tokenSecurityPort.getUsername(token);
    }
}
