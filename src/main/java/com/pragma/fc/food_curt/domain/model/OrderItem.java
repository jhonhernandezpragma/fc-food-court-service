package com.pragma.fc.food_curt.domain.model;

public class OrderItem {
    private Dish dish;
    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(Dish dish, Integer quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
