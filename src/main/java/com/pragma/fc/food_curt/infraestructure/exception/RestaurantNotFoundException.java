package com.pragma.fc.food_curt.infraestructure.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long nit) {
        super("Restaurant with nit: " + nit + " not found");
    }
}
