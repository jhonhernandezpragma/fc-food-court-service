package com.pragma.fc.food_curt.domain.api;

import com.pragma.fc.food_curt.domain.model.Dish;

public interface IDishServicePort {
    Dish createDish(Dish dish, Long ownerDocumentNumber);
    Dish updateDish(Integer id, Double price, String description, Long ownerDocumentNumber);
    Dish updateDishStatus(Integer id, Boolean isActive, Long ownerDocumentNumber);
}
