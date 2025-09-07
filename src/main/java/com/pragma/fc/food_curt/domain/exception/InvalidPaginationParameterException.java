package com.pragma.fc.food_curt.domain.exception;

public class InvalidPaginationParameterException extends RuntimeException {
    public InvalidPaginationParameterException(String message) {
        super(message);
    }
}
