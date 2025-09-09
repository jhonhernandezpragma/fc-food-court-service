package com.pragma.fc.food_curt.infraestructure.exception;

public class InvalidRestaurantOrderException extends RuntimeException {
    public InvalidRestaurantOrderException() {
        super("All dishes must belong to the restaurant provided.");
    }
}
