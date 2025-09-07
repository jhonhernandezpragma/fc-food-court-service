package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.DishListItemResponseDto;
import com.pragma.fc.food_curt.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {IDishListItemResponseMapper.class}
)
public interface IDishPaginationResponseMapper extends IPaginationResponseMapper<DishListItemResponseDto, Dish> {
}
