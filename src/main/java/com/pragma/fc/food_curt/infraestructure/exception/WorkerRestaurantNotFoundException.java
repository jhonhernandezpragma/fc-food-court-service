package com.pragma.fc.food_curt.infraestructure.exception;

public class WorkerRestaurantNotFoundException extends RuntimeException {
    public WorkerRestaurantNotFoundException(Long workerDocumentNumber) {
        super("No restaurant was found associated with the worker having document: " + workerDocumentNumber);

    }
}
