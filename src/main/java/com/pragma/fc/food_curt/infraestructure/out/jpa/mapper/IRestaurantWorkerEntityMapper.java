package com.pragma.fc.food_curt.infraestructure.out.jpa.mapper;

import com.pragma.fc.food_curt.domain.usecase.output.UseCaseRestaurantWorkerOutput;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantWorkerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantWorkerEntityMapper {

    @Mapping(target = "restaurant", source = "restaurantEntity")
    @Mapping(target = "workerDocumentNumber", source = "userDocumentNumber")
    RestaurantWorkerEntity toEntity(RestaurantEntity restaurantEntity, Long userDocumentNumber);

    @Mapping(target = "restaurantNit", source = "restaurant.nit")
    UseCaseRestaurantWorkerOutput toModel(RestaurantWorkerEntity entity);
}

