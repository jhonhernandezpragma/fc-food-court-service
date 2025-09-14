package com.pragma.fc.food_curt.infraestructure.out.jpa.mapper;

import com.pragma.fc.food_curt.domain.model.OrderOtp;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderOtpEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IOrderOtpEntityMapper {
    OrderOtpEntity toEntity(OrderOtp orderOtp);
}
