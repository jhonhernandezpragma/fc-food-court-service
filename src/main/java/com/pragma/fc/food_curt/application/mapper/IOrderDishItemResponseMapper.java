package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.OrderDishItemResponseDto;
import com.pragma.fc.food_curt.domain.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderDishItemResponseMapper {
    @Mapping(target = "dishId", source = "dish.id")
    @Mapping(target = "name", source = "dish.name")
    @Mapping(target = "unitPrice", source = "dish.price")
    @Named("modelToDto")
    OrderDishItemResponseDto toDto(OrderItem item);
}
