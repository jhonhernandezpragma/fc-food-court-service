package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.OrderResponseDto;
import com.pragma.fc.food_curt.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {IOrderResponseMapper.class}
)
public interface IOrderPaginationResponseMapper extends IPaginationResponseMapper<OrderResponseDto, Order> {
}
