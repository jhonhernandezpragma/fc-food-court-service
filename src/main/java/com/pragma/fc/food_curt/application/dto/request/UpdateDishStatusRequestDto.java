package com.pragma.fc.food_curt.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDishStatusRequestDto {
    @NotNull
    private Boolean isActive;
}
