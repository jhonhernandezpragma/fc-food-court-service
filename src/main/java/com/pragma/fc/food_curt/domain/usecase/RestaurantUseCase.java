package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidNameException;
import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidUserRoleException;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.IRestaurantPersistencePort;
import com.pragma.fc.food_curt.domain.spi.IUserClientPort;

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
}
