package com.pragma.fc.food_curt.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequestDto {
    @NotNull
    @PositiveOrZero
    private Long restaurantNit;

    @NotNull
    @NotEmpty
    @Valid
    private List<OrderDishItemRequestDto> items;
}
