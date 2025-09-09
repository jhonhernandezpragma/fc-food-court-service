package com.pragma.fc.food_curt.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusTest {

    @Test
    void shouldContainAllOrderStatuses() {
        assertThat(OrderStatus.values())
                .containsExactly(
                        OrderStatus.PENDING,
                        OrderStatus.IN_PREPARATION,
                        OrderStatus.CANCELED,
                        OrderStatus.READY,
                        OrderStatus.DELIVERED
                );
    }

    @Test
    void shouldGetOrderStatusByName() {
        assertThat(OrderStatus.valueOf("PENDING")).isEqualTo(OrderStatus.PENDING);
        assertThat(OrderStatus.valueOf("DELIVERED")).isEqualTo(OrderStatus.DELIVERED);
    }
}
