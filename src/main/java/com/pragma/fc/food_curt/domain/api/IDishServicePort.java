package com.pragma.fc.food_curt.domain.api;

import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.model.Pagination;

import java.util.Optional;

public interface IDishServicePort {
    Dish createDish(Dish dish, Long ownerDocumentNumber);
    Dish updateDish(Integer id, Double price, String description, Long ownerDocumentNumber);
    Dish updateDishStatus(Integer id, Boolean isActive, Long ownerDocumentNumber);
    Pagination<Dish> getPaginatedByCategoryIdSortedByName(int page, int size, Optional<Integer> categoryId);
}
