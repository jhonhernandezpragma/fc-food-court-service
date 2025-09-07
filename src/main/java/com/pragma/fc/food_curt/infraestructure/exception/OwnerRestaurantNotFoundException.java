package com.pragma.fc.food_curt.infraestructure.exception;

public class OwnerRestaurantNotFoundException extends RuntimeException {
    public OwnerRestaurantNotFoundException(Long ownerDocumentNumber) {
        super("No se encontró un restaurante asociado al owner con documento: " + ownerDocumentNumber);
    }
}
