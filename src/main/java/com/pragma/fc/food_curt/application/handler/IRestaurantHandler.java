package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateRestaurantResponseDto;

public interface IRestaurantHandler {
    CreateRestaurantResponseDto createRestaurant(CreateRestaurantRequestDto dto);
}
