package com.pragma.fc.food_curt.domain.api;

import com.pragma.fc.food_curt.domain.model.Restaurant;

public interface IRestaurantServicePort {
    Restaurant createRestaurant(Restaurant restaurant);
}
