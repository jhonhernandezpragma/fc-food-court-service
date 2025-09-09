package com.pragma.fc.food_curt.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    void shouldCreateOrderItemUsingAllArgsConstructor() {
        Dish dish = new Dish(
                1,
                new DishCategory(10, "Category"),
                new Restaurant(123L, 456L, "Resto", "Address", "Phone", "Logo"),
                "Dish name",
                "Dish description",
                10000.0,
                "https://example.com/dish.jpg",
                true
        );

        OrderItem item = new OrderItem(dish, 3, 123.2d);

        assertThat(item.getDish()).isEqualTo(dish);
        assertThat(item.getQuantity()).isEqualTo(3);
        assertThat(item.getPrice()).isEqualTo(123.2d);
    }

    @Test
    void shouldCreateOrderItemWithSetters() {
        Dish dish = new Dish();
        dish.setId(1);
        dish.setName("Dish name");
        dish.setPrice(20000.0);

        OrderItem item = new OrderItem();
        item.setDish(dish);
        item.setQuantity(2);
        item.setPrice(123.2d);

        assertThat(item.getDish().getName()).isEqualTo("Dish name");
        assertThat(item.getQuantity()).isEqualTo(2);
        assertThat(item.getPrice()).isEqualTo(123.2d);
    }
}
