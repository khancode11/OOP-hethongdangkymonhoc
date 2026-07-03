package com.example.registration.exception;

public class CreditLimitException extends Exception {
    public CreditLimitException(String message) {
        super(message);
    }
}