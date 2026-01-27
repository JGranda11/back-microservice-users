package com.pragma.challenge.msvc_users.domain.spi.security;

import com.pragma.challenge.msvc_users.domain.model.auth.AuthenticationInfo;

public interface AuthenticationSecurityPort {
    void authenticate(AuthenticationInfo authenticationInfo);
}
