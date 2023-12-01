package com.example.kunuzdemo.exceptions;

public class SendVerificationCodeException extends RuntimeException {
    public SendVerificationCodeException(String message) {
        super(message);
    }
}
