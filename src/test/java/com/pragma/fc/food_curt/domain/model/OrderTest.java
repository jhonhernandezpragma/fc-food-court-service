package com.pragma.fc.food_curt.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void shouldCreateOrderUsingAllArgsConstructor() {
        Restaurant restaurant = new Restaurant(123L, 456L, "Resto", "Address", "Phone", "Logo");

        Dish dish1 = new Dish(1, null, restaurant, "Dish1", "Desc1", 10000.0, "url1", true);
        Dish dish2 = new Dish(2, null, restaurant, "Dish2", "Desc2", 5000.0, "url2", true);

        OrderItem item1 = new OrderItem(dish1, 2);
        OrderItem item2 = new OrderItem(dish2, 3);

        LocalDateTime now = LocalDateTime.now();

        Order order = new Order(
                10,
                99999999L,
                restaurant,
                OrderStatus.PENDING,
                now,
                List.of(item1, item2)
        );

        assertThat(order.getOrderId()).isEqualTo(10);
        assertThat(order.getCustomerDocumentNumber()).isEqualTo(99999999L);
        assertThat(order.getRestaurant()).isEqualTo(restaurant);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getCreatedAt()).isEqualTo(now);
        assertThat(order.getItems()).containsExactly(item1, item2);
        assertThat(order.getTotal()).isEqualTo(2 * 10000.0 + 3 * 5000.0);
    }

    @Test
    void shouldCreateOrderWithSetters() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNit(123L);
        restaurant.setName("My Restaurant");

        Dish dish = new Dish();
        dish.setId(1);
        dish.setPrice(15000.0);

        OrderItem item = new OrderItem();
        item.setDish(dish);
        item.setQuantity(2);

        Order order = new Order();
        order.setOrderId(20);
        order.setCustomerDocumentNumber(88888888L);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.READY);
        order.setCreatedAt(LocalDateTime.of(2025, 9, 8, 12, 30));
        order.setItems(List.of(item));

        assertThat(order.getOrderId()).isEqualTo(20);
        assertThat(order.getCustomerDocumentNumber()).isEqualTo(88888888L);
        assertThat(order.getRestaurant().getName()).isEqualTo("My Restaurant");
        assertThat(order.getStatus()).isEqualTo(OrderStatus.READY);
        assertThat(order.getItems()).hasSize(1);
        assertThat(order.getTotal()).isEqualTo(2 * 15000.0);
    }
}
