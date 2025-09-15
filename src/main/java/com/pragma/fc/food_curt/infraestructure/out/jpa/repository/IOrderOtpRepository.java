package com.pragma.fc.food_curt.infraestructure.out.jpa.repository;

import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderOtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderOtpRepository extends JpaRepository<OrderOtpEntity, Integer> {
    Optional<OrderOtpEntity> findFirstByOrderIdOrderByCreatedAtDesc(Integer orderId);
}
