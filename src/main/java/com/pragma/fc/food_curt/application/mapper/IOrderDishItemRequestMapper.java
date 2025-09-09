package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.request.OrderDishItemRequestDto;
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
public interface IOrderDishItemRequestMapper {
    @Named("dtoToModel")
    @Mapping(target = "dish.id", source = "dishId")
    OrderItem toModel(OrderDishItemRequestDto dto);
}
