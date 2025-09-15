package com.pragma.fc.food_curt.domain.exception;

public class OrderNotInReadyException extends RuntimeException {

    public OrderNotInReadyException(Integer orderId, String currentStatus) {
        super(String.format(
                "Order %d is currently in status '%s' and cannot be marked as delivered.",
                orderId, currentStatus
        ));
    }
}
