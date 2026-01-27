package com.pragma.challenge.msvc_users.domain.exception;

public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException() {
        super(String.format("Password or Email incorrect"));
    }
}
