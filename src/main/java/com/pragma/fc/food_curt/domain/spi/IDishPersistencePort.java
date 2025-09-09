package com.pragma.fc.food_curt.domain.spi;

import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.model.Pagination;

import java.util.List;
import java.util.Optional;

public interface IDishPersistencePort {
    Dish createDish(Dish dish);

    Dish updateDish(Integer id, Double price, String description);

    Dish updateDishStatus(Integer id, Boolean isActive);

    Boolean existsDishByIdAndRestaurantOwner(Integer dishId, Long ownerDocumentNumber);

    Pagination<Dish> getPaginatedByCategoryIdSortedByName(int page, int size, Optional<Integer> categoryId);

    boolean allBelongToSameRestaurant(List<Integer> ids);

    List<Dish> getAllByIds(List<Integer> ids);

    boolean belongToRestaurant(Integer dishId, Long restaurantNit);
}
