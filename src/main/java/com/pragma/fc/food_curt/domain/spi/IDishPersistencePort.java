package com.pragma.fc.food_curt.domain.spi;

import com.pragma.fc.food_curt.domain.model.Dish;

public interface IDishPersistencePort {
    Dish createDish(Dish dish);
    Dish updateDish(Integer id, Double price, String description);
    Dish updateDishStatus(Integer id, Boolean isActive);
    Boolean existsDishByIdAndRestaurantOwner(Integer dishId, Long ownerDocumentNumber);
}
