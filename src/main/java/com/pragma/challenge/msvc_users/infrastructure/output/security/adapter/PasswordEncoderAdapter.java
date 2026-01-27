package com.pragma.challenge.msvc_users.infrastructure.output.security.adapter;

import com.pragma.challenge.msvc_users.domain.spi.security.IPasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements IPasswordEncoderPort {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
