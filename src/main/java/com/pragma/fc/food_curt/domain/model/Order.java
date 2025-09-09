package com.pragma.fc.food_curt.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Integer orderId;
    private Long customerDocumentNumber;
    private Long workerDocumentNumber;
    private Restaurant restaurant;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItem> items;

    public Order() {
    }

    public Order(Integer orderId, Long customerDocumentNumber, Long workerDocumentNumber, Restaurant restaurant, OrderStatus status, LocalDateTime createdAt, List<OrderItem> items) {
        this.orderId = orderId;
        this.customerDocumentNumber = customerDocumentNumber;
        this.workerDocumentNumber = workerDocumentNumber;
        this.restaurant = restaurant;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Long getCustomerDocumentNumber() {
        return customerDocumentNumber;
    }

    public void setCustomerDocumentNumber(Long customerDocumentNumber) {
        this.customerDocumentNumber = customerDocumentNumber;
    }

    public Long getWorkerDocumentNumber() {
        return workerDocumentNumber;
    }

    public void setWorkerDocumentNumber(Long workerDocumentNumber) {
        this.workerDocumentNumber = workerDocumentNumber;
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getDish().getPrice() * item.getQuantity())
                .sum();
    }
}
