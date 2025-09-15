package com.pragma.fc.food_curt.infraestructure.exception;

public class OrderOtpNotFoundException extends RuntimeException {
    public OrderOtpNotFoundException(Integer orderId) {
        super("No OTP was found for order with ID: " + orderId);
    }
}
