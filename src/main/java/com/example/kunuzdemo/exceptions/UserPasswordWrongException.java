package com.example.kunuzdemo.exceptions;

public class UserPasswordWrongException extends RuntimeException {
    public UserPasswordWrongException(String message) {
        super(message);
    }
}
