package com.pragma.fc.food_curt.infraestructure.exception;

public class OrderStatusNotFoundException extends RuntimeException {
    public OrderStatusNotFoundException() {
        super("Order status not found");
    }
}
