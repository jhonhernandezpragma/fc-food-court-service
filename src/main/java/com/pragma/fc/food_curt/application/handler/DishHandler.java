package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.request.UpdateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.request.UpdateDishStatusRequestDto;
import com.pragma.fc.food_curt.application.dto.response.DishListItemResponseDto;
import com.pragma.fc.food_curt.application.dto.response.DishResponseDto;
import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;
import com.pragma.fc.food_curt.application.mapper.ICreateDishRequestMapper;
import com.pragma.fc.food_curt.application.mapper.IDishPaginationResponseMapper;
import com.pragma.fc.food_curt.application.mapper.IDishResponseMapper;
import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.spi.ITokenServicePort;
import com.pragma.fc.food_curt.infraestructure.input.security.entity.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DishHandler implements IDishHandler {
    private final ICreateDishRequestMapper createDishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;
    private final IDishServicePort dishServicePort;
    private final ITokenServicePort tokenServicePort;
    private final IDishPaginationResponseMapper paginationResponseMapper;

    @Override
    public DishResponseDto createDish(CreateDishRequestDto dto) {
        Long ownerDocumentNumber = extractDocumentNumber();
        Dish dish = createDishRequestMapper.toModel(dto);
        Dish newDish = dishServicePort.createDish(dish, ownerDocumentNumber);
        return dishResponseMapper.toDto(newDish);
    }

    @Override
    public DishResponseDto updateDish(Integer id, UpdateDishRequestDto dto) {
        Long ownerDocumentNumber = extractDocumentNumber();
        Dish updatedDish = dishServicePort.updateDish(id, dto.getPrice(), dto.getDescription(), ownerDocumentNumber);
        return dishResponseMapper.toDto(updatedDish);
    }

    @Override
    public DishResponseDto updateDishStatus(Integer id, UpdateDishStatusRequestDto dto) {
        Long ownerDocumentNumber = extractDocumentNumber();
        Dish updatedDish = dishServicePort.updateDishStatus(id, dto.getIsActive(), ownerDocumentNumber);
        return dishResponseMapper.toDto(updatedDish);
    }

    private Long extractDocumentNumber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long documentNumber = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            documentNumber = Long.parseLong(tokenServicePort
                    .extractSubject(jwtAuthenticationToken.getToken())
            );
        }

        return documentNumber;
    }

    @Override
    public PaginationResponseDto<DishListItemResponseDto> getPaginatedByCategoryIdSortedByName(int page, int size, Optional<Integer> categoryId) {
        Pagination<Dish> dishPagination = dishServicePort.getPaginatedByCategoryIdSortedByName(page, size, categoryId);
        return paginationResponseMapper.toDto(dishPagination);
    }
}
