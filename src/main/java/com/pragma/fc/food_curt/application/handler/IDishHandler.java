package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.request.UpdateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.request.UpdateDishStatusRequestDto;
import com.pragma.fc.food_curt.application.dto.response.DishListItemResponseDto;
import com.pragma.fc.food_curt.application.dto.response.DishResponseDto;
import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;

import java.util.Optional;

public interface IDishHandler {
    DishResponseDto createDish(CreateDishRequestDto dto);

    DishResponseDto updateDish(Integer id, UpdateDishRequestDto dto);

    DishResponseDto updateDishStatus(Integer id, UpdateDishStatusRequestDto dto);

    PaginationResponseDto<DishListItemResponseDto> getPaginatedByCategoryIdSortedByName(int page, int size, Optional<Integer> categoryId);
}
