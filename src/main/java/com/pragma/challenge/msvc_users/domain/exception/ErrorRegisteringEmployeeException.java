package com.pragma.challenge.msvc_users.domain.exception;

public class ErrorRegisteringEmployeeException extends RuntimeException {
    public ErrorRegisteringEmployeeException() {
        super("An error has occurred registering user in restaurant");
    }
}
