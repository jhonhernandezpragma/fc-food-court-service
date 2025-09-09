package com.pragma.fc.food_curt.domain.exception;

public class DuplicateDishIdException extends RuntimeException {
    public DuplicateDishIdException() {
        super("There are duplicate dish IDs in the order");
    }
}
