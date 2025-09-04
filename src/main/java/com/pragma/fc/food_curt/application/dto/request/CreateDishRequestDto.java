package com.pragma.fc.food_curt.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateDishRequestDto {
    @PositiveOrZero
    private Integer categoryId;

    @PositiveOrZero
    @NotNull
    private Long restaurantNit;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 500)
    private String description;

    @Positive
    @NotNull
    private Double price;

    @NotNull
    @NotBlank
    @Size(max = 500)
    private String imageUrl;
}
