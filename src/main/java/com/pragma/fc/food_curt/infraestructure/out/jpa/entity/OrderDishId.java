package com.pragma.fc.food_curt.infraestructure.out.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Setter
@Getter
public class OrderDishId implements Serializable {
    private Integer orderId;
    private Integer dishId;
}
