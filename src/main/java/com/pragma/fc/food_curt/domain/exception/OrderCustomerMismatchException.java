package com.pragma.fc.food_curt.domain.exception;

public class OrderCustomerMismatchException extends RuntimeException {
    public OrderCustomerMismatchException(Integer orderId) {
        super(String.format(
                "Order %d is assigned to another customer. You are not authorized to mark it as ready.",
                orderId
        ));
    }
}
