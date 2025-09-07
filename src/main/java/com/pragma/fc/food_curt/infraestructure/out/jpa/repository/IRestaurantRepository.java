package com.pragma.fc.food_curt.infraestructure.out.jpa.repository;

import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @Query("SELECT r.nit FROM RestaurantEntity r WHERE r.ownerDocumentNumber = :ownerDocumentNumber")
    Optional<Long> findNitByOwnerDocumentNumber(Long ownerDocumentNumber);
    Boolean existsByNitAndOwnerDocumentNumber(Long restaurantNit, Long ownerDocumentNumber);
}