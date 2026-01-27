package com.pragma.challenge.msvc_users.domain.spi.security;

public interface IPasswordEncoderPort {
    String encode(String password);
}
