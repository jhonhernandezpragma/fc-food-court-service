package com.pragma.fc.food_curt.domain.exception;

public class DishNonPositivePriceException extends RuntimeException {
    public DishNonPositivePriceException(Double price) {
        super("Price " + price + " should be positive");
    }
}
