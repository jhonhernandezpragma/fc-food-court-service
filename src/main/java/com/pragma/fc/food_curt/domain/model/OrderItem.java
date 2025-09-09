package com.pragma.fc.food_curt.domain.model;

public class OrderItem {
    private Dish dish;
    private Integer quantity;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(Dish dish, Integer quantity, Double price) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
