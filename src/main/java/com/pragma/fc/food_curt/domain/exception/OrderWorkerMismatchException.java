package com.pragma.fc.food_curt.domain.exception;

public class OrderWorkerMismatchException extends RuntimeException {
    public OrderWorkerMismatchException(Integer orderId) {
        super(String.format(
                "Order %d is assigned to another worker or is not assigned yet. You are not authorized to mark it as ready.",
                orderId
        ));
    }
}
