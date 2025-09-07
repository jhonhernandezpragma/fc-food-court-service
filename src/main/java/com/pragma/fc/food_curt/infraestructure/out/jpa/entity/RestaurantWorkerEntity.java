package com.pragma.fc.food_curt.infraestructure.out.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "restaurant_workers")
@Data
public class RestaurantWorkerEntity {
    @Id
    private Long workerDocumentNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_nit")
    private RestaurantEntity restaurant;
}
