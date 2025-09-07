package com.pragma.fc.food_curt.domain.exception;

public class OwnerNotAuthorizedForRestaurantException extends RuntimeException {
    public OwnerNotAuthorizedForRestaurantException() {
        super("Owner is not authorized to manage restaurant");
    }
}
