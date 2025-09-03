package com.pragma.fc.food_curt.infraestructure.out.jpa.mapper;


import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEntityMapper {
    RestaurantEntity toEntity(Restaurant restaurant);
    Restaurant toModel(RestaurantEntity entity);
}
