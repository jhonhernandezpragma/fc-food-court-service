package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateDishResponseDto;
import com.pragma.fc.food_curt.application.mapper.ICreateDishRequestMapper;
import com.pragma.fc.food_curt.application.mapper.ICreateDishResponseMapper;
import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.model.Dish;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DishHandler implements IDishHandler {
    private final ICreateDishRequestMapper createDishRequestMapper;
    private final ICreateDishResponseMapper createDishResponseMapper;
    private final IDishServicePort dishServicePort;

    @Override
    public CreateDishResponseDto createDish(CreateDishRequestDto dto) {
        Dish dish = createDishRequestMapper.toModel(dto);
        Dish newDish = dishServicePort.createDish(dish);
        return createDishResponseMapper.toDto(newDish);
    }
}
