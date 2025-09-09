package com.pragma.fc.food_curt.infraestructure.out.jpa.repository;

import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantWorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantWorkerRepository extends JpaRepository<RestaurantWorkerEntity, Long> {
    Optional<RestaurantWorkerEntity> findRestaurantByWorkerDocumentNumber(Long workerDocumentNumber);
}
