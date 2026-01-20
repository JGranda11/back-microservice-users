package com.pragma.challenge.msvc_users.domain.exception;

public class UnderAgedUserException extends RuntimeException {
    public UnderAgedUserException() {
        super("An actual user cannot be under 18 years old");
    }
}
