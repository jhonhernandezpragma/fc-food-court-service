package com.pragma.fc.food_curt.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateOrderResponseDto {
    private Integer orderId;
    private Long restaurantNit;
    private String status;
    private List<OrderDishItemResponseDto> items;
    private Double total;
    private LocalDateTime createdAt;
}
