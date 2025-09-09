package com.pragma.fc.food_curt.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishItemRequestDto {
    @NotNull
    @PositiveOrZero
    private Integer dishId;

    @NotNull
    @Positive
    private Integer quantity;
}
