package com.pragma.fc.food_curt.infraestructure.out.jpa.repository;

import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDishRepository extends JpaRepository<DishEntity, Integer> {
    boolean existsByIdAndRestaurantOwnerDocumentNumber(Integer dishId, Long ownerDocumentNumber);

    @Query("SELECT d FROM DishEntity d WHERE d.category.id = :categoryId")
    Page<DishEntity> findAllByCategoryId(Integer categoryId, Pageable pageable);
}
