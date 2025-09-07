package com.pragma.fc.food_curt.infraestructure.out.jpa.repository;

import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantWorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantWorkerRepository extends JpaRepository<RestaurantWorkerEntity, Long> {
}
