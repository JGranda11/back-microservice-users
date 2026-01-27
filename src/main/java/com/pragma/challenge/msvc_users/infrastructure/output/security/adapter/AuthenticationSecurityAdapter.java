package com.pragma.challenge.msvc_users.infrastructure.output.security.adapter;

import com.pragma.challenge.msvc_users.domain.model.auth.AuthenticationInfo;
import com.pragma.challenge.msvc_users.domain.spi.security.AuthenticationSecurityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSecurityAdapter implements AuthenticationSecurityPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public void authenticate(AuthenticationInfo authenticationInfo) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationInfo.getId(),
                        authenticationInfo.getPassword()
                )
        );
    }
}
