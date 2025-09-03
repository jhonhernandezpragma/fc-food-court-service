package com.pragma.fc.food_curt.domain.entity;

import com.pragma.fc.food_curt.domain.model.Restaurant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTest {

    @Test
    void shouldCreateRestaurantCorrectlyWithAllFields() {
        Restaurant restaurant = new Restaurant(
                123L,
                213L,
                "Restaurant name",
                "Restaurant address",
                "Restaurant phone",
                "Restaurant logo url"
        );

        assertThat(restaurant.getNit()).isEqualTo(123L);
        assertThat(restaurant.getOwnerDocumentNumber()).isEqualTo(213L);
        assertThat(restaurant.getName()).isEqualTo("Restaurant name");
        assertThat(restaurant.getAddress()).isEqualTo("Restaurant address");
        assertThat(restaurant.getPhone()).isEqualTo("Restaurant phone");
        assertThat(restaurant.getLogoUrl()).isEqualTo("Restaurant logo url");
    }
}
