package com.pragma.fc.food_curt.infraestructure.out.jpa.mapper;

import com.pragma.fc.food_curt.domain.model.DishCategory;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.DishCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDishCategoryEntityMapper {
    @Named("modelToEntity")
    DishCategoryEntity toEntity(DishCategory dishCategory);
    @Named("entityToModel")
    DishCategory toModel(DishCategoryEntity entity);
}
