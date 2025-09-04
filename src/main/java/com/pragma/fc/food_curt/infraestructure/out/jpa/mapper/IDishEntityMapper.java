package com.pragma.fc.food_curt.infraestructure.out.jpa.mapper;


import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {IDishCategoryEntityMapper.class, IRestaurantEntityMapper.class}
)
public interface IDishEntityMapper {
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "modelToEntity")
    @Mapping(target = "category", source = "category", qualifiedByName = "modelToEntity")
    DishEntity toEntity(Dish dish);

    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "entityToModel")
    @Mapping(target = "category", source = "category", qualifiedByName = "entityToModel")
    Dish toModel(DishEntity entity);
}
