package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.RestaurantSummaryResponseDto;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantSummaryResponseMapper {
    @Named("modelToDto")
    RestaurantSummaryResponseDto toDto(Restaurant restaurant);
}
