package com.pragma.fc.food_curt.domain.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException() {
        super("Owner not found");
    }

    public OwnerNotFoundException(String message) {
        super(message);
    }
}
