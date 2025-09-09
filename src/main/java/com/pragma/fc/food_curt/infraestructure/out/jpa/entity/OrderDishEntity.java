package com.pragma.fc.food_curt.infraestructure.out.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders_dishes")
@Getter
@Setter
public class OrderDishEntity {
    @EmbeddedId
    private OrderDishId id;

    @ManyToOne(optional = false)
    @MapsId("orderId")
    // la PK orderId (this entity) se relaciona (FK) con PK de OrderEntity. Evita tene 2 columnas + evita tener q setear ambas manualmente
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(optional = false)
    @MapsId("dishId")
    @JoinColumn(name = "dish_id", nullable = false)
    private DishEntity dish;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;
}
