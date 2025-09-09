package com.pragma.fc.food_curt.infraestructure.exception;

public class OwnerRestaurantNotFoundException extends RuntimeException {
    public OwnerRestaurantNotFoundException(Long ownerDocumentNumber) {
        super("No restaurant was found associated with the owner having document: " + ownerDocumentNumber);
    }
}
