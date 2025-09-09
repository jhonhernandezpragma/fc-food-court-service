package com.pragma.fc.food_curt.infraestructure.out.jpa.adapter;

import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.model.Pagination;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

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

        if (price != null) dishEntity.setPrice(price);
        if (description != null) dishEntity.setDescription(description);

        DishEntity updatedDish = dishRepository.save(dishEntity);
        return dishEntityMapper.toModel(updatedDish);
    }

    @Override
    public Dish updateDishStatus(Integer id, Boolean isActive) {
        DishEntity dishEntity = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(id));

        dishEntity.setIsActive(isActive);
        DishEntity updatedDish = dishRepository.save(dishEntity);
        return dishEntityMapper.toModel(updatedDish);
    }

    @Override
    public Boolean existsDishByIdAndRestaurantOwner(Integer dishId, Long ownerDocumentNumber) {
        return dishRepository.existsByIdAndRestaurantOwnerDocumentNumber(dishId, ownerDocumentNumber);
    }

    @Override
    public Pagination<Dish> getPaginatedByCategoryIdSortedByName(int page, int size, Optional<Integer> categoryId) {
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<DishEntity> dishEntityPage = categoryId
                .map(id -> dishRepository.findAllByCategoryId(id, pageable))
                .orElseGet(() -> dishRepository.findAll(pageable));

        Pagination<Dish> pagination = new Pagination<>();
        pagination.setItems(dishEntityPage.getContent()
                .stream()
                .map(dishEntityMapper::toModel)
                .toList()
        );
        pagination.setCurrentPageNumber(page);
        pagination.setCurrentItemCount(dishEntityPage.getNumberOfElements());
        pagination.setFirstPage(dishEntityPage.isFirst());
        pagination.setLastPage(dishEntityPage.isLast());
        pagination.setTotalItems(dishEntityPage.getTotalElements());
        pagination.setTotalPages(dishEntityPage.getTotalPages());
        pagination.setPageSize(size);

        return pagination;
    }

    @Override
    public boolean allBelongToSameRestaurant(List<Integer> ids) {
        return dishRepository.countDistinctRestaurantsByDishIds(ids) == 1;
    }

    @Override
    public List<Dish> getAllByIds(List<Integer> ids) {
        return dishRepository.findAllById(ids)
                .stream()
                .map(dishEntityMapper::toModel)
                .toList();
    }

    @Override
    public boolean belongToRestaurant(Integer dishId, Long restaurantNit) {
        return restaurantNit.equals(dishRepository.findRestaurantNitByDishId(dishId));
    }
}
