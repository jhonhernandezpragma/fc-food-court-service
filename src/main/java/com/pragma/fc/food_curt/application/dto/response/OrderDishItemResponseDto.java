package com.pragma.fc.food_curt.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishItemResponseDto {
    private Integer dishId;
    private String name;
    private Integer quantity;
    private Double unitPrice;
}
