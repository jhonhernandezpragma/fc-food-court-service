package com.pragma.fc.food_curt.infraestructure.out.jpa.adapter;

import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.spi.IDishPersistencePort;
import com.pragma.fc.food_curt.infraestructure.exception.DishCategoryNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.DishNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.RestaurantNotFoundException;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.DishCategoryEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.DishEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IDishCategoryRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IDishRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IRestaurantRepository;

public class DishJpaAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IDishCategoryRepository dishCategoryRepository;
    private final IDishEntityMapper dishEntityMapper;

    public DishJpaAdapter(IDishRepository dishRepository, IRestaurantRepository restaurantRepository, IDishCategoryRepository dishCategoryRepository, IDishEntityMapper dishEntityMapper) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishCategoryRepository = dishCategoryRepository;
        this.dishEntityMapper = dishEntityMapper;
    }

    @Override
    public Dish createDish(Dish dish) {
        DishEntity dishEntity = dishEntityMapper.toEntity(dish);

        DishCategoryEntity dishCategoryEntity = dishCategoryRepository.findById(dish.getCategory().getId())
                .orElseThrow(() -> new DishCategoryNotFoundException(dish.getCategory().getId()));

        RestaurantEntity restaurantEntity = restaurantRepository.findById(dish.getRestaurant().getNit())
                .orElseThrow(() -> new RestaurantNotFoundException(dish.getRestaurant().getNit()));

        dishEntity.setCategory(dishCategoryEntity);
        dishEntity.setRestaurant(restaurantEntity);

        DishEntity newDishEntity = dishRepository.save(dishEntity);
        return dishEntityMapper.toModel(newDishEntity);
    }

    @Override
    public Dish updateDish(Integer id, Double price, String description) {
        DishEntity dishEntity = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(id));

        if(price != null) dishEntity.setPrice(price);
        if(description != null) dishEntity.setDescription(description);

        DishEntity updatedDish = dishRepository.save(dishEntity);
        return dishEntityMapper.toModel(updatedDish);
    }
}
