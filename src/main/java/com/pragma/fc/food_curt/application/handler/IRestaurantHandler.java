package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;
import com.pragma.fc.food_curt.application.dto.response.RestaurantListItemDto;
import com.pragma.fc.food_curt.application.dto.response.RestaurantResponseDto;
import com.pragma.fc.food_curt.application.dto.response.WorkerRestaurantResponseDto;

public interface IRestaurantHandler {
    RestaurantResponseDto createRestaurant(CreateRestaurantRequestDto dto);
    Long getRestaurantNitByOwner();
    WorkerRestaurantResponseDto assignWorkerToRestaurant(Long restaurantNit, Long userDocumentNumber);
    PaginationResponseDto<RestaurantListItemDto> getAllPaginatedAndSortedByName(int page, int size);
}
