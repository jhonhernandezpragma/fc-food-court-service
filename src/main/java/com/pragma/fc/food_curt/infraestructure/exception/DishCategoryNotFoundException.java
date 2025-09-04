package com.pragma.fc.food_curt.infraestructure.exception;

public class DishCategoryNotFoundException extends RuntimeException {
    public DishCategoryNotFoundException(Integer id) {
        super("Dish category: " + id + " not found");
    }
}
