package com.pragma.fc.food_curt.domain.exception;

public class DishesFromDifferentRestaurantsException extends RuntimeException {
    public DishesFromDifferentRestaurantsException() {
        super("All dishes in an order must belong to the same restaurant.");
    }
}