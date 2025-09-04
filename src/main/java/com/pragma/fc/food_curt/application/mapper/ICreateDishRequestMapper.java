package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.request.CreateDishRequestDto;
import com.pragma.fc.food_curt.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ICreateDishRequestMapper {

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "restaurant.nit", source = "restaurantNit")
    Dish toModel(CreateDishRequestDto dto);
}
