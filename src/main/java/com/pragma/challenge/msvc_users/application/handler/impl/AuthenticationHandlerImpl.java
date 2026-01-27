package com.pragma.challenge.msvc_users.application.handler.impl;

import com.pragma.challenge.msvc_users.application.dto.request.AuthenticationRequest;
import com.pragma.challenge.msvc_users.application.dto.response.AuthenticationResponse;
import com.pragma.challenge.msvc_users.application.handler.AuthenticationHandler;
import com.pragma.challenge.msvc_users.application.mapper.response.AuthenticationResponseMapper;
import com.pragma.challenge.msvc_users.domain.api.security.AuthenticationServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationHandlerImpl implements AuthenticationHandler {
    private final AuthenticationServicePort authenticationServicePort;
    private final AuthenticationResponseMapper authenticationResponseMapper;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        return authenticationResponseMapper.toResponse(
                authenticationServicePort.authenticate(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                ));
    }

    @Override
    public AuthenticationResponse validateToken(String token) {
        return authenticationResponseMapper.toResponse(
                authenticationServicePort.validateToken(token)
        );
    }
}
