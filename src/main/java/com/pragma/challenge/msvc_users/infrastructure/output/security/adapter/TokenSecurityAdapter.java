package com.pragma.challenge.msvc_users.infrastructure.output.security.adapter;

import com.pragma.challenge.msvc_users.domain.exception.InvalidTokenException;
import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.spi.security.TokenSecurityPort;
import com.pragma.challenge.msvc_users.infrastructure.output.security.service.JwtService;
import com.pragma.challenge.msvc_users.infrastructure.output.security.util.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenSecurityAdapter implements TokenSecurityPort {

    private final JwtService jwtService;

    @Override
    public String createToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put(SecurityConstants.CLAIM_ROLE,
                SecurityConstants.ROLE_PREFIX + user.getRole().getName().name());
        return jwtService.generateToken(claims, String.valueOf(user.getId()));
    }

    @Override
    public boolean validateToken(String token, Long username) {
        try {
            return jwtService.isTokenValid(token, String.valueOf(username));
        } catch (InvalidTokenException e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Long getUsername(String token) {
        return Long.valueOf(jwtService.extractUsername(token));
    }
}
