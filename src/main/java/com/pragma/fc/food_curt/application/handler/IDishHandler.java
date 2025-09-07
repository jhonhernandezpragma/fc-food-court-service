package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.request.UpdateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.request.UpdateDishStatusRequestDto;
import com.pragma.fc.food_curt.application.dto.response.DishResponseDto;

public interface IDishHandler {
    DishResponseDto createDish(CreateDishRequestDto dto);
    DishResponseDto updateDish(Integer id, UpdateDishRequestDto dto);
    DishResponseDto updateDishStatus(Integer id, UpdateDishStatusRequestDto dto);
}
