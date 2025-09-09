package com.pragma.fc.food_curt.infraestructure.out.jpa.repository;

import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<OrderEntity, Integer> {
    boolean existsByCustomerDocumentNumber(Long customerDocumentNumber);

    Page<OrderEntity> findByStatusIdAndRestaurantNit(Integer statusId, Long restaurantNit, Pageable pageable);

    Page<OrderEntity> findAllByRestaurantNit(Long restaurantNit, Pageable pageable);
}