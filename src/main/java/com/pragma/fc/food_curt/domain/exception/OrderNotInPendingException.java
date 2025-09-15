package com.pragma.fc.food_curt.domain.exception;

public class OrderNotInPendingException extends RuntimeException {

    public OrderNotInPendingException(Integer orderId, String currentStatus) {
        super(String.format(
                "Order %d is currently in status '%s' and cannot be marked as canceled.",
                orderId, currentStatus
        ));
    }
}
