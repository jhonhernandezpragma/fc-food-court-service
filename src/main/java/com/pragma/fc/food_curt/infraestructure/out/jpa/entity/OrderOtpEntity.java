package com.pragma.fc.food_curt.infraestructure.out.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_otps")
@Data
public class OrderOtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer orderId;

    private String code;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    private Boolean used;
}
