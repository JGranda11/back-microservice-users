package com.pragma.challenge.msvc_users.application.handler;

import com.pragma.challenge.msvc_users.application.dto.request.AuthenticationRequest;
import com.pragma.challenge.msvc_users.application.dto.response.AuthenticationResponse;

public interface AuthenticationHandler {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    AuthenticationResponse validateToken(String token);
}
