package com.pragma.fc.food_curt.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishListItemResponseDto {
    private Integer id;
    private String name;
    private String description;
    private Long price;
    private String imageUrl;
    private Boolean isActive;
}
