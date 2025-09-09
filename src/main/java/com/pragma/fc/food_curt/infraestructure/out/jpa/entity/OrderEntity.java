package com.pragma.fc.food_curt.infraestructure.out.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // For Cascade flush optimization
    private Integer orderId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_nit")
    private RestaurantEntity restaurant;

    @Column(nullable = false)
    private Long customerDocumentNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id")
    private OrderStatusEntity status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDishEntity> orderDishes = new HashSet<>();
}
