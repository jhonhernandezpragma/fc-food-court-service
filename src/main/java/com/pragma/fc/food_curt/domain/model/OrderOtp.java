package com.pragma.fc.food_curt.domain.model;

import java.time.LocalDateTime;

public class OrderOtp {
    private Integer id;
    private Integer orderId;
    private String code;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private Boolean used;

    public OrderOtp(Integer id, Integer orderId, String code, LocalDateTime expiresAt, LocalDateTime createdAt, Boolean used) {
        this.id = id;
        this.orderId = orderId;
        this.code = code;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.used = used;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}
