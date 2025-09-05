package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidNameException;
import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidUserRoleException;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.IRestaurantPersistencePort;
import com.pragma.fc.food_curt.domain.spi.IUserClientPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantUseCaseTest {
    @InjectMocks
    RestaurantUseCase restaurantUseCase;

    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    IUserClientPort userClientPort;

    Restaurant createRestaurant(String name) {
        return new Restaurant(
                123L,
                213L,
                name,
                "Restaurant address",
                "Restaurant phone",
                "Restaurant logo url"
        );
    }

    @Test
    void shouldCreateRestaurantSuccessfully() {
        when(userClientPort.isOwner(213L, "token")).thenReturn(true);
        when(restaurantPersistencePort.createRestaurant(any(Restaurant.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Restaurant restaurant = createRestaurant("Restaurant name");

        Restaurant newRestaurant = restaurantUseCase.createRestaurant(restaurant, "token");

        assertThat(newRestaurant.getNit()).isEqualTo(restaurant.getNit());
        assertThat(newRestaurant.getOwnerDocumentNumber()).isEqualTo(restaurant.getOwnerDocumentNumber());
        assertThat(newRestaurant.getName()).isEqualTo(restaurant.getName());
        assertThat(newRestaurant.getAddress()).isEqualTo(restaurant.getAddress());
        assertThat(newRestaurant.getPhone()).isEqualTo(restaurant.getPhone());
        assertThat(newRestaurant.getLogoUrl()).isEqualTo(restaurant.getLogoUrl());
    }

    @Test
    void shouldPersistRestaurantWhenCreateIsCalled() {
        when(userClientPort.isOwner(213L, "token")).thenReturn(true);
        when(restaurantPersistencePort.createRestaurant(any(Restaurant.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Restaurant restaurant = createRestaurant("Restaurant name");

        Restaurant newRestaurant = restaurantUseCase.createRestaurant(restaurant, "token");

        verify(restaurantPersistencePort).createRestaurant(newRestaurant);
    }

    @Test
    void shouldFailToCreateRestaurantWithNameWithOnlyNumbers() {
        Restaurant restaurant = createRestaurant("99999009");

        assertThatThrownBy(() -> restaurantUseCase.createRestaurant(restaurant, "token"))
                .isInstanceOf(RestaurantInvalidNameException.class)
                .hasMessageContaining("99999009");
    }

    @Test
    void shouldFailToCreateRestaurantWhenOwnerDocumentDoesNotBelongToOwner() {
        when(userClientPort.isOwner(213L, "token")).thenReturn(false);

        Restaurant restaurant = createRestaurant("Restaurant name");

        assertThatThrownBy(() -> restaurantUseCase.createRestaurant(restaurant, "token"))
                .isInstanceOf(RestaurantInvalidUserRoleException.class)
                .hasMessageContaining("213");
    }

}
