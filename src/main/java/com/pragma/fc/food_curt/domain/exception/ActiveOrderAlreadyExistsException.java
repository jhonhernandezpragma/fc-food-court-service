package com.pragma.fc.food_curt.domain.exception;

public class ActiveOrderAlreadyExistsException extends RuntimeException {
    public ActiveOrderAlreadyExistsException(Long customerDocumentNumber) {
        super("Customer with document " + customerDocumentNumber + " already has an active order.");
    }
}