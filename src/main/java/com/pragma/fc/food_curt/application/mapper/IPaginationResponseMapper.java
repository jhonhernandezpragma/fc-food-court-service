package com.pragma.fc.food_curt.application.mapper;

import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;
import com.pragma.fc.food_curt.domain.model.Pagination;


public interface IPaginationResponseMapper<T, D> {
    PaginationResponseDto<T> toDto(Pagination<D> pagination);
}
