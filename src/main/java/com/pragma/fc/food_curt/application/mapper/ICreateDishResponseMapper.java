package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.CreateDishResponseDto;
import com.pragma.fc.food_curt.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {IDishCategoryResponseMapper.class, IRestaurantSummaryResponseMapper.class}
)
public interface ICreateDishResponseMapper {

    @Mapping(target = "category", source = "category", qualifiedByName = "modelToDto")
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "modelToDto")
    CreateDishResponseDto toDto(Dish dish);
}
