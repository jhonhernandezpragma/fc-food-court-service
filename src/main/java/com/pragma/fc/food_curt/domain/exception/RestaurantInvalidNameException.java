package com.pragma.fc.food_curt.domain.exception;

public class RestaurantInvalidNameException extends RuntimeException {
    public RestaurantInvalidNameException(String restaurantName) {
        super("Invalid restaurant with name: " + restaurantName);
    }
}
