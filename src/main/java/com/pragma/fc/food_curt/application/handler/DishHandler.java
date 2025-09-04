package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.request.UpdateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.response.DishResponseDto;
import com.pragma.fc.food_curt.application.mapper.ICreateDishRequestMapper;
import com.pragma.fc.food_curt.application.mapper.IDishResponseMapper;
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
    private final IDishResponseMapper dishResponseMapper;
    private final IDishServicePort dishServicePort;

    @Override
    public DishResponseDto createDish(CreateDishRequestDto dto) {
        Dish dish = createDishRequestMapper.toModel(dto);
        Dish newDish = dishServicePort.createDish(dish);
        return dishResponseMapper.toDto(newDish);
    }

    @Override
    public DishResponseDto updateDish(Integer id, UpdateDishRequestDto dto) {
        Dish updatedDish = dishServicePort.updateDish(id, dto.getPrice(), dto.getDescription());
        return dishResponseMapper.toDto(updatedDish);
    }
}
