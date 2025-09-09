package com.pragma.fc.food_curt.domain.exception;

public class OrderAlreadyAssignedException extends RuntimeException {
    public OrderAlreadyAssignedException(Integer orderId, Long currentWorker) {
        super("Order " + orderId + " is already assigned to worker " + currentWorker + ".");
    }
}
