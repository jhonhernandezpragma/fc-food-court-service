package com.pragma.fc.food_curt.infraestructure.out.jpa.adapter;

import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.IRestaurantPersistencePort;
import com.pragma.fc.food_curt.domain.usecase.output.UseCaseRestaurantWorkerOutput;
import com.pragma.fc.food_curt.infraestructure.exception.OwnerRestaurantNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.RestaurantAlreadyExistsException;
import com.pragma.fc.food_curt.infraestructure.exception.RestaurantNotFoundException;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantWorkerEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IRestaurantWorkerEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IRestaurantRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IRestaurantWorkerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RestaurantJpaAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantWorkerRepository restaurantWorkerRepository;
    private final IRestaurantWorkerEntityMapper restaurantWorkerEntityMapper;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    public RestaurantJpaAdapter(IRestaurantRepository restaurantRepository, IRestaurantWorkerRepository restaurantWorkerRepository, IRestaurantWorkerEntityMapper restaurantWorkerEntityMapper, IRestaurantEntityMapper restaurantEntityMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantWorkerRepository = restaurantWorkerRepository;
        this.restaurantWorkerEntityMapper = restaurantWorkerEntityMapper;
        this.restaurantEntityMapper = restaurantEntityMapper;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);

        if(restaurantRepository.existsById(restaurant.getNit())) {
            throw new RestaurantAlreadyExistsException();
        }

        RestaurantEntity newRestaurantEntity = restaurantRepository.save(restaurantEntity);
        return restaurantEntityMapper.toModel(newRestaurantEntity);
    }

    @Override
    public Long getRestaurantNitByOwner(Long ownerDocumentNumber) {
        return restaurantRepository.findNitByOwnerDocumentNumber(ownerDocumentNumber)
                .orElseThrow(() -> new OwnerRestaurantNotFoundException(ownerDocumentNumber));
    }

    @Override
    public UseCaseRestaurantWorkerOutput assignWorkerToRestaurant(Long restaurantNit, Long userDocumentNumber) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantNit)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantNit));
        RestaurantWorkerEntity restaurantWorkerEntity = restaurantWorkerEntityMapper.toEntity(restaurantEntity, userDocumentNumber);
        RestaurantWorkerEntity newRestaurantWorkerEntity = restaurantWorkerRepository.save(restaurantWorkerEntity);
        return restaurantWorkerEntityMapper.toModel(newRestaurantWorkerEntity);
    }

    @Override
    public Boolean existsWorkerByDocumentNumber(Long workerDocumentNumber) {
        return restaurantWorkerRepository.existsById(workerDocumentNumber);
    }

    @Override
    public Boolean existsRestaurantByOwner(Long restaurantNit, Long ownerDocumentNumber) {
        return restaurantRepository.existsByNitAndOwnerDocumentNumber(restaurantNit, ownerDocumentNumber);
    }

    @Override
    public Pagination<Restaurant> getAllPaginatedAndSortedByName(int page, int size) {
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(page-1, size, sort);

        Page<RestaurantEntity> restaurantEntityPage = restaurantRepository.findAll(pageable);

        Pagination<Restaurant> pagination = new Pagination();
        pagination.setItems(restaurantEntityPage.getContent()
                .stream()
                .map(restaurantEntityMapper::toModel)
                .toList()
        );
        pagination.setCurrentPageNumber(page);
        pagination.setCurrentItemCount(restaurantEntityPage.getNumberOfElements());
        pagination.setFirstPage(restaurantEntityPage.isFirst());
        pagination.setLastPage(restaurantEntityPage.isLast());
        pagination.setTotalItems(restaurantEntityPage.getTotalElements());
        pagination.setTotalPages(restaurantEntityPage.getTotalPages());
        pagination.setPageSize(size);

        return pagination;
    }
}
