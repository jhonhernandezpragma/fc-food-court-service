package com.pragma.fc.food_curt.application.handler;

import com.pragma.fc.food_curt.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;
import com.pragma.fc.food_curt.application.dto.response.RestaurantListItemDto;
import com.pragma.fc.food_curt.application.dto.response.RestaurantResponseDto;
import com.pragma.fc.food_curt.application.dto.response.WorkerRestaurantResponseDto;
import com.pragma.fc.food_curt.application.mapper.ICreateRestaurantRequestMapper;
import com.pragma.fc.food_curt.application.mapper.IRestaurantPaginationMapper;
import com.pragma.fc.food_curt.application.mapper.IRestaurantResponseMapper;
import com.pragma.fc.food_curt.application.mapper.IWorkerRestaurantResponseMapper;
import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.ITokenServicePort;
import com.pragma.fc.food_curt.domain.usecase.output.UseCaseRestaurantWorkerOutput;
import com.pragma.fc.food_curt.infraestructure.input.security.entity.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler {
    private final IRestaurantResponseMapper createRestaurantResponseMapper;
    private final ICreateRestaurantRequestMapper createRestaurantRequestMapper;
    private final IRestaurantServicePort restaurantServicePort;
    private final IWorkerRestaurantResponseMapper workerRestaurantResponseMapper;
    private final ITokenServicePort tokenServicePort;
    private final IRestaurantPaginationMapper restaurantPaginationMapper;

    @Override
    public RestaurantResponseDto createRestaurant(CreateRestaurantRequestDto dto) {
        Restaurant restaurant = createRestaurantRequestMapper.toModel(dto);
        Restaurant newRestaurant = restaurantServicePort.createRestaurant(restaurant);
        return createRestaurantResponseMapper.toDto(newRestaurant);
    }

    @Override
    public Long getRestaurantNitByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long ownerDocumentNumber = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            ownerDocumentNumber = Long.parseLong(tokenServicePort
                    .extractSubject(jwtAuthenticationToken.getToken())
            );
        }

        return restaurantServicePort.getRestaurantNitByOwner(ownerDocumentNumber);
    }

    @Override
    public WorkerRestaurantResponseDto assignWorkerToRestaurant(Long restaurantNit, Long userDocumentNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long ownerDocumentNumber = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            ownerDocumentNumber = Long.parseLong(tokenServicePort
                    .extractSubject(jwtAuthenticationToken.getToken())
            );
        }

        UseCaseRestaurantWorkerOutput output = restaurantServicePort.assignWorkerToRestaurant(restaurantNit, userDocumentNumber, ownerDocumentNumber);
        return workerRestaurantResponseMapper.toDto(output);
    }

    @Override
    public PaginationResponseDto<RestaurantListItemDto> getAllPaginatedAndSortedByName(int page, int size) {
        Pagination<Restaurant> restaurantPagination = restaurantServicePort.getAllPaginatedAndSortedByName(page, size);
        return restaurantPaginationMapper.toDto(restaurantPagination);
    }
}
