package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.exception.DishNonPositivePriceException;
import com.pragma.fc.food_curt.domain.exception.InvalidPaginationParameterException;
import com.pragma.fc.food_curt.domain.exception.OwnerNotAuthorizedForRestaurantException;
import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.spi.IDishPersistencePort;

import java.util.List;
import java.util.Optional;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantServicePort restaurantServicePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantServicePort restaurantServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantServicePort = restaurantServicePort;
    }

    @Override
    public Dish createDish(Dish dish, Long ownerDocumentNumber) {
        if (!isMyRestaurant(dish.getRestaurant().getNit(), ownerDocumentNumber)) {
            throw new OwnerNotAuthorizedForRestaurantException();
        }

        validatePrice(dish.getPrice());
        dish.setIsActive(true);
        return dishPersistencePort.createDish(dish);
    }

    @Override
    public Dish updateDish(Integer id, Double price, String description, Long ownerDocumentNumber) {
        if (!isMyDish(id, ownerDocumentNumber)) {
            throw new OwnerNotAuthorizedForRestaurantException();
        }

        validatePrice(price);
        return dishPersistencePort.updateDish(id, price, description);
    }

    @Override
    public Dish updateDishStatus(Integer id, Boolean isActive, Long ownerDocumentNumber) {
        if (!isMyDish(id, ownerDocumentNumber)) {
            throw new OwnerNotAuthorizedForRestaurantException();
        }

        return dishPersistencePort.updateDishStatus(id, isActive);
    }

    @Override
    public Pagination<Dish> getPaginatedByCategoryIdSortedByName(int page, int size, Optional<Integer> categoryId) {
        if (page < 1) {
            throw new InvalidPaginationParameterException("Page must be greater than or equal to 1");
        }

        if (size > 100) {
            throw new InvalidPaginationParameterException("Page size must be less than or equal to 100");
        }

        return dishPersistencePort.getPaginatedByCategoryIdSortedByName(page, size, categoryId);
    }

    @Override
    public boolean allBelongToSameRestaurant(List<Integer> ids) {
        return dishPersistencePort.allBelongToSameRestaurant(ids);
    }

    @Override
    public List<Dish> getAllByIds(List<Integer> ids) {
        return dishPersistencePort.getAllByIds(ids);
    }

    @Override
    public boolean belongToRestaurant(Integer dishId, Long restaurantNit) {
        return dishPersistencePort.belongToRestaurant(dishId, restaurantNit);
    }

    private void validatePrice(Double price) {
        if (price <= 0) {
            throw new DishNonPositivePriceException(price);
        }
    }

    private boolean isMyDish(Integer dishId, Long ownerDocumentNumber) {
        return dishPersistencePort.existsDishByIdAndRestaurantOwner(dishId, ownerDocumentNumber);
    }


    private boolean isMyRestaurant(Long restaurantNit, Long ownerDocumentNumber) {
        return restaurantServicePort.existsRestaurantByOwner(restaurantNit, ownerDocumentNumber);
    }
}
