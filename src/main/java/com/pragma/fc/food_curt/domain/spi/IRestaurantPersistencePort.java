package com.pragma.fc.food_curt.domain.spi;

import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.usecase.output.UseCaseRestaurantWorkerOutput;

public interface IRestaurantPersistencePort {
    Restaurant createRestaurant(Restaurant restaurant);

    Long getRestaurantNitByOwner(Long ownerDocumentNumber);

    UseCaseRestaurantWorkerOutput assignWorkerToRestaurant(Long restaurantNit, Long userDocumentNumber);

    Boolean existsWorkerByDocumentNumber(Long workerDocumentNumber);

    Boolean existsRestaurantByOwner(Long restaurantNit, Long ownerDocumentNumber);

    Pagination<Restaurant> getAllPaginatedAndSortedByName(int page, int size);

    Long getRestaurantNitByWorker(Long workerDocumentNumber);
}
