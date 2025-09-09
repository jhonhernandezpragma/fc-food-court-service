package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.request.CreateOrderRequestDto;
import com.pragma.fc.food_curt.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {IOrderDishItemRequestMapper.class}

)
public interface ICreateOrderRequestMapper {
    @Mapping(target = "restaurant.nit", source = "restaurantNit")
    @Mapping(target = "items", source = "items", qualifiedByName = "dtoToModel")
    Order toModel(CreateOrderRequestDto dto);
}
