package com.pragma.fc.food_curt.domain.spi;

import com.pragma.fc.food_curt.domain.model.Restaurant;

public interface IRestaurantPersistencePort {
    Restaurant createRestaurant(Restaurant restaurant);
}
