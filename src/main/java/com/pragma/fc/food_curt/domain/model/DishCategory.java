package com.pragma.fc.food_curt.domain.model;

public class DishCategory {
    private Integer id;
    private String name;

    public DishCategory() {
    }

    public DishCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
