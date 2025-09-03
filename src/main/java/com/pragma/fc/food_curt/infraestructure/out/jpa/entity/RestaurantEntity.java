package com.pragma.fc.food_curt.infraestructure.out.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "restaurants")
@Data
public class RestaurantEntity {
    @Id
    private Long nit;

    @Column(nullable = false)
    private Long ownerDocumentNumber;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 250)
    private String address;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 400)
    private String logoUrl;
}
