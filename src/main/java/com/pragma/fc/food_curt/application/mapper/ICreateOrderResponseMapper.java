package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.CreateOrderResponseDto;
import com.pragma.fc.food_curt.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = IOrderDishItemResponseMapper.class
)
public interface ICreateOrderResponseMapper {
    @Mapping(target = "items", source = "items", qualifiedByName = "modelToDto")
    @Mapping(target = "total", expression = "java(order.getTotal())")
    @Mapping(target = "restaurantNit", expression = "java(order.getRestaurant().getNit())")
    CreateOrderResponseDto toDto(Order order);
}
