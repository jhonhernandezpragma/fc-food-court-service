package com.pragma.fc.food_curt.domain.exception;

public class OrderNotInPreparationException extends RuntimeException {

    public OrderNotInPreparationException(Integer orderId, String currentStatus) {
        super(String.format(
                "Order %d is currently in status '%s' and cannot be marked as ready.",
                orderId, currentStatus
        ));
    }
}
