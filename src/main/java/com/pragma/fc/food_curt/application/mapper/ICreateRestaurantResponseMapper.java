package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.CreateRestaurantResponseDto;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ICreateRestaurantResponseMapper {
    CreateRestaurantResponseDto toDto(Restaurant restaurant);
}
