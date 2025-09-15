package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateOrderRequestDto;
import com.pragma.fc.food_curt.application.dto.response.OrderResponseDto;
import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;

import java.util.Optional;

public interface IOrderHandler {
    OrderResponseDto createOrder(CreateOrderRequestDto dto);

    PaginationResponseDto<OrderResponseDto> getPaginatedByStatusSortedByDate(int page, int size, Optional<Integer> statusId);

    OrderResponseDto assignWorkerToOrder(Integer orderId);

    OrderResponseDto markAsReady(Integer orderId);

    OrderResponseDto finishOrder(Integer orderId, String otpCode);
}
