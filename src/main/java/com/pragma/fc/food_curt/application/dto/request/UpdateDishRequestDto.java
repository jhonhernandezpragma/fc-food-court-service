package com.pragma.fc.food_curt.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDishRequestDto {
    @NotNull
    @Positive
    private Double price;

    @NotNull
    @NotBlank
    @Size(max = 500)
    private String description;
}
