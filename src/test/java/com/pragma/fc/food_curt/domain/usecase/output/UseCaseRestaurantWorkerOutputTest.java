package com.pragma.fc.food_curt.domain.usecase.output;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UseCaseRestaurantWorkerOutputTest {
    @Test
    void shouldCreateRestaurantWorkerOutputCorrectlyWithAllFields() {
        UseCaseRestaurantWorkerOutput restaurant = new UseCaseRestaurantWorkerOutput();
        restaurant.setRestaurantNit(213L);
        restaurant.setWorkerDocumentNumber(123L);

        assertThat(restaurant.getWorkerDocumentNumber()).isEqualTo(123L);
        assertThat(restaurant.getRestaurantNit()).isEqualTo(213L);
    }

    @Test
    void shouldCreateRestaurantWorkerOutputCorrectlyUsingAllArgsConstructor() {
        UseCaseRestaurantWorkerOutput restaurant = new UseCaseRestaurantWorkerOutput(
                123L,
                213L
        );

        assertThat(restaurant.getWorkerDocumentNumber()).isEqualTo(123L);
        assertThat(restaurant.getRestaurantNit()).isEqualTo(213L);

    }
}
