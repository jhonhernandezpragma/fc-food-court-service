package com.pragma.fc.food_curt.domain.api;

import com.pragma.fc.food_curt.domain.model.Dish;

public interface IDishServicePort {
    Dish createDish(Dish dish);
}
