package com.pragma.fc.food_curt.domain.exception;

public class WorkerAlreadyAssignedException extends RuntimeException {
    public WorkerAlreadyAssignedException() {
        super("Worker already assigned");
    }
    public WorkerAlreadyAssignedException(String message) {
        super(message);
    }
}