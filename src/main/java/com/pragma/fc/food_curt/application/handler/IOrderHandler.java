package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateOrderRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateOrderResponseDto;

public interface IOrderHandler {
    CreateOrderResponseDto createOrder(CreateOrderRequestDto dto);
}
