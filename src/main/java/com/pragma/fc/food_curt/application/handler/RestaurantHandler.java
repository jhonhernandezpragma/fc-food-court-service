package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateRestaurantResponseDto;
import com.pragma.fc.food_curt.application.mapper.ICreateRestaurantRequestMapper;
import com.pragma.fc.food_curt.application.mapper.ICreateRestaurantResponseMapper;
import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler {
    private final ICreateRestaurantResponseMapper createRestaurantResponseMapper;
    private final ICreateRestaurantRequestMapper createRestaurantRequestMapper;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public CreateRestaurantResponseDto createRestaurant(CreateRestaurantRequestDto dto) {
        Restaurant restaurant = createRestaurantRequestMapper.toModel(dto);
        Restaurant newRestaurant = restaurantServicePort.createRestaurant(restaurant);
        return createRestaurantResponseMapper.toDto(newRestaurant);
    }
}
