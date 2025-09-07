package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateRestaurantResponseDto;
import com.pragma.fc.food_curt.application.dto.response.WorkerRestaurantResponseDto;

public interface IRestaurantHandler {
    CreateRestaurantResponseDto createRestaurant(CreateRestaurantRequestDto dto);
    Long getRestaurantNitByOwner();
    WorkerRestaurantResponseDto assignWorkerToRestaurant(Long restaurantNit, Long userDocumentNumber);
}
