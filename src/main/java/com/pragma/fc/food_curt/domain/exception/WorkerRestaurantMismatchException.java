package com.pragma.fc.food_curt.domain.exception;

public class WorkerRestaurantMismatchException extends RuntimeException {
    public WorkerRestaurantMismatchException(Integer orderId, Long workerDocumentNumber) {
        super("Worker " + workerDocumentNumber + " cannot be assigned to order " + orderId +
                " because they belong to a different restaurant.");
    }
}

