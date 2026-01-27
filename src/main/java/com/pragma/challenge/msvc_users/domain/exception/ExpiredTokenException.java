package com.pragma.challenge.msvc_users.domain.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("Given token has expired");
    }
}
