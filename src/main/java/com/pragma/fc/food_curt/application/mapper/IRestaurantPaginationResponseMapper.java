package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.RestaurantListItemResponseDto;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = IRestaurantListItemResponseMapper.class
)
public interface IRestaurantPaginationResponseMapper extends IPaginationResponseMapper<RestaurantListItemResponseDto, Restaurant> {
}
