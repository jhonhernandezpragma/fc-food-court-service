package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.DishCategoryResponseDto;
import com.pragma.fc.food_curt.domain.model.DishCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDishCategoryResponseMapper {
    @Named("modelToDto")
    DishCategoryResponseDto toDto(DishCategory category);
}
