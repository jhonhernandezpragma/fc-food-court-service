package com.pragma.fc.food_curt.domain.exception;

public class OrderOtpInvalidException extends RuntimeException {
    public OrderOtpInvalidException() {
        super("Invalid order OTP: it does not exist or has already been used.");
    }
}
