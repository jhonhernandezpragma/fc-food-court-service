package com.pragma.fc.food_curt.infraestructure.exception;

public class RestaurantAlreadyExistsException extends RuntimeException {
    public RestaurantAlreadyExistsException() {
        super("Restaurant already exists.");
    }
}
