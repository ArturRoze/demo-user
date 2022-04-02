package com.example.demouser.exception;

public class PasswordException extends RuntimeException {

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
