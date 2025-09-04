package com.pragma.fc.food_curt.infraestructure.out.jpa.repository;

import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.DishCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishCategoryRepository extends JpaRepository<DishCategoryEntity, Integer> {
}
