package com.pragma.fc.food_curt.infraestructure.out.jpa.repository;

import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderStatusRepository extends JpaRepository<OrderStatusEntity, Integer> {
    Optional<OrderStatusEntity> findByName(String name);
}
