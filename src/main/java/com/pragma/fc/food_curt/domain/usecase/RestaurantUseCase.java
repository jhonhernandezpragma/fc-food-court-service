package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.exception.InvalidPaginationParameterException;
import com.pragma.fc.food_curt.domain.exception.OwnerNotFoundException;
import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidNameException;
import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidUserRoleException;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.IRestaurantPersistencePort;
import com.pragma.fc.food_curt.domain.spi.IUserClientPort;
import com.pragma.fc.food_curt.domain.usecase.output.UseCaseRestaurantWorkerOutput;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClientPort userClientPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserClientPort userClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        if(restaurant.getName().matches("\\d+")) {
            throw new RestaurantInvalidNameException(restaurant.getName());
        }

        if(!userClientPort.isOwner(restaurant.getOwnerDocumentNumber())) {
            throw new RestaurantInvalidUserRoleException(restaurant.getOwnerDocumentNumber());
        }

        return restaurantPersistencePort.createRestaurant(restaurant);
    }

    @Override
    public Long getRestaurantNitByOwner(Long ownerDocumentNumber) {
        return restaurantPersistencePort.getRestaurantNitByOwner(ownerDocumentNumber);
    }

    @Override
    public UseCaseRestaurantWorkerOutput assignWorkerToRestaurant(Long restaurantNit, Long userDocumentNumber, Long ownerDocumentNumber) {
        Long ownerRestaurantNit = getRestaurantNitByOwner(ownerDocumentNumber);
        if (ownerRestaurantNit == null || !ownerRestaurantNit.equals(restaurantNit) ) {
            throw new OwnerNotFoundException("Owner with document " + ownerDocumentNumber + " is not associated with any restaurant");
        }

//        TODO: pendiente definir estrategia de coreografia + ACID entre BDs distribuidas
//        if (restaurantPersistencePort.existsWorkerByDocumentNumber(userDocumentNumber)) {
//            throw new WorkerAlreadyAssignedException("Worker with document " + userDocumentNumber + " is already assigned to a restaurant");
//        }

        return restaurantPersistencePort.assignWorkerToRestaurant(restaurantNit, userDocumentNumber);
    }

    @Override
    public Boolean existsRestaurantByOwner(Long restaurantNit, Long ownerDocumentNumber) {
        return restaurantPersistencePort.existsRestaurantByOwner(restaurantNit, ownerDocumentNumber);
    }

    @Override
    public Pagination<Restaurant> getAllPaginatedAndSortedByName(int page, int size) {
        if(page < 1) {
            throw new InvalidPaginationParameterException("Page must be greater than or equal to 1");
        }

        if(size > 100) {
            throw new InvalidPaginationParameterException("Page size must be less than or equal to 100");
        }

        return restaurantPersistencePort.getAllPaginatedAndSortedByName(page, size);
    }
}
