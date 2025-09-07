package com.pragma.fc.food_curt.infraestructure.exception;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(Integer id) {
        super("Dish: " + id + " not found");
    }

    public DishNotFoundException() {
        super("Dish: not found");
    }
}
