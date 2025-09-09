package com.pragma.fc.food_curt.infraestructure.out.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "dishes")
@Check(constraints = "price > 0")
@Data
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private DishCategoryEntity category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_nit")
    private RestaurantEntity restaurant;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, length = 400)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean isActive;

}
