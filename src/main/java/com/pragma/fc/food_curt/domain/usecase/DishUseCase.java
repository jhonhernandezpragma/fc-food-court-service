package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.exception.DishNonPositivePriceException;
import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.spi.IDishPersistencePort;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public Dish createDish(Dish dish) {
        if(dish.getPrice() <= 0) {
            throw new DishNonPositivePriceException(dish.getPrice());
        }

        dish.setActive(true);

        return dishPersistencePort.createDish(dish);

    }
}
