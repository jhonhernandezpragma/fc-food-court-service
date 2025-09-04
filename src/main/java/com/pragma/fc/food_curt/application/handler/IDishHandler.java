package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateDishResponseDto;

public interface IDishHandler {
    CreateDishResponseDto createDish(CreateDishRequestDto dto);
}
