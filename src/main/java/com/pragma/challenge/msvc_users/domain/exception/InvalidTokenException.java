package com.pragma.challenge.msvc_users.domain.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Given token is invalid");
    }
}
