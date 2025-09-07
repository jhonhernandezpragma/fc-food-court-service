package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.exception.OwnerNotFoundException;
import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidNameException;
import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidUserRoleException;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.IRestaurantPersistencePort;
import com.pragma.fc.food_curt.domain.spi.IUserClientPort;
import com.pragma.fc.food_curt.domain.usecase.output.UseCaseRestaurantWorkerOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
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
        when(userClientPort.isOwner(213L)).thenReturn(true);
        when(restaurantPersistencePort.createRestaurant(any(Restaurant.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Restaurant restaurant = createRestaurant("Restaurant name");

        Restaurant newRestaurant = restaurantUseCase.createRestaurant(restaurant);

        assertThat(newRestaurant.getNit()).isEqualTo(restaurant.getNit());
        assertThat(newRestaurant.getOwnerDocumentNumber()).isEqualTo(restaurant.getOwnerDocumentNumber());
        assertThat(newRestaurant.getName()).isEqualTo(restaurant.getName());
        assertThat(newRestaurant.getAddress()).isEqualTo(restaurant.getAddress());
        assertThat(newRestaurant.getPhone()).isEqualTo(restaurant.getPhone());
        assertThat(newRestaurant.getLogoUrl()).isEqualTo(restaurant.getLogoUrl());
    }

    @Test
    void shouldPersistRestaurantWhenCreateIsCalled() {
        when(userClientPort.isOwner(213L)).thenReturn(true);
        when(restaurantPersistencePort.createRestaurant(any(Restaurant.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Restaurant restaurant = createRestaurant("Restaurant name");

        Restaurant newRestaurant = restaurantUseCase.createRestaurant(restaurant);

        verify(restaurantPersistencePort).createRestaurant(newRestaurant);
    }

    @Test
    void shouldFailToCreateRestaurantWithNameWithOnlyNumbers() {
        Restaurant restaurant = createRestaurant("99999009");

        assertThatThrownBy(() -> restaurantUseCase.createRestaurant(restaurant))
                .isInstanceOf(RestaurantInvalidNameException.class)
                .hasMessageContaining("99999009");
    }

    @Test
    void shouldFailToCreateRestaurantWhenOwnerDocumentDoesNotBelongToOwner() {
        when(userClientPort.isOwner(213L)).thenReturn(false);

        Restaurant restaurant = createRestaurant("Restaurant name");

        assertThatThrownBy(() -> restaurantUseCase.createRestaurant(restaurant))
                .isInstanceOf(RestaurantInvalidUserRoleException.class)
                .hasMessageContaining("213");
    }

    void shouldCallGetRestaurantNitByOwner() {
        when(restaurantPersistencePort.getRestaurantNitByOwner(123L))
                .thenReturn(444L);

        Long restaurantNit = restaurantUseCase.getRestaurantNitByOwner(123L);

        assertThat(restaurantNit).isEqualTo(444L);
        verify(restaurantPersistencePort).getRestaurantNitByOwner(444L);
    }

    @Test
    void shouldAssignWorkerToRestaurantWhenOwnerMatches() {
        Long restaurantNit = 123L;
        Long userDocumentNumber = 456L;
        Long ownerDocumentNumber = 789L;

        when(restaurantPersistencePort.getRestaurantNitByOwner(ownerDocumentNumber))
                .thenReturn(restaurantNit);
        UseCaseRestaurantWorkerOutput expectedOutput =
                new UseCaseRestaurantWorkerOutput(restaurantNit, userDocumentNumber);

        when(restaurantPersistencePort.assignWorkerToRestaurant(restaurantNit, userDocumentNumber))
                .thenReturn(expectedOutput);

        UseCaseRestaurantWorkerOutput result = restaurantUseCase.assignWorkerToRestaurant(
                restaurantNit, userDocumentNumber, ownerDocumentNumber);

        assertThat(expectedOutput).isEqualTo(result);
        verify(restaurantPersistencePort).assignWorkerToRestaurant(restaurantNit, userDocumentNumber);
    }

    @Test
    void shouldThrowOwnerNotFoundExceptionWhenOwnerDoesNotMatchRestaurant() {
        Long restaurantNit = 123L;
        Long userDocumentNumber = 456L;
        Long ownerDocumentNumber = 789L;

        when(restaurantPersistencePort.getRestaurantNitByOwner(ownerDocumentNumber))
                .thenReturn(999L);

        assertThatThrownBy(() ->
                restaurantUseCase.assignWorkerToRestaurant(restaurantNit, userDocumentNumber, ownerDocumentNumber)
        ).isInstanceOf(OwnerNotFoundException.class);

        verify(restaurantPersistencePort, never()).assignWorkerToRestaurant(anyLong(), anyLong());
    }
}
