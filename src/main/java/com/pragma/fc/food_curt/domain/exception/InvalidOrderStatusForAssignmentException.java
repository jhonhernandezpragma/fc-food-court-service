package com.pragma.fc.food_curt.domain.exception;

public class InvalidOrderStatusForAssignmentException extends RuntimeException {
    public InvalidOrderStatusForAssignmentException(Integer orderId, String status) {
        super("Order " + orderId + " cannot be assigned because it is in status " + status + ".");
    }
}
