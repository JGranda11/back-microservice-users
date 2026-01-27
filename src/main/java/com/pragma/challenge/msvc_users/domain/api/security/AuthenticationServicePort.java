package com.pragma.challenge.msvc_users.domain.api.security;

import com.pragma.challenge.msvc_users.domain.model.auth.AuthenticatedUser;

public interface AuthenticationServicePort {
    AuthenticatedUser authenticate(String email, String password);
    AuthenticatedUser validateToken(String token);
}
