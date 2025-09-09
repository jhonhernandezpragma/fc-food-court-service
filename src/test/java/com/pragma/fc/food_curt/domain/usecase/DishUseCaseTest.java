package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.exception.DishNonPositivePriceException;
import com.pragma.fc.food_curt.domain.exception.InvalidPaginationParameterException;
import com.pragma.fc.food_curt.domain.exception.OwnerNotAuthorizedForRestaurantException;
import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.model.DishCategory;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @InjectMocks
    DishUseCase dishUseCase;

    @Mock
    IDishPersistencePort dishPersistencePort;

    @Mock
    IRestaurantServicePort restaurantServicePort;

    Dish createDish(Double price) {
        DishCategory category = new DishCategory();
        category.setId(1);
        category.setName("Dish category name");

        Restaurant restaurant = new Restaurant();
        restaurant.setNit(123L);
        restaurant.setOwnerDocumentNumber(213L);
        restaurant.setName("Restaurant name");
        restaurant.setAddress("Restaurant address");
        restaurant.setPhone("Restaurant phone");
        restaurant.setLogoUrl("Restaurant logo url");

        Dish dish = new Dish();
        dish.setId(10);
        dish.setCategory(category);
        dish.setRestaurant(restaurant);
        dish.setName("Delicious Dish");
        dish.setDescription("A tasty and healthy dish description");
        dish.setPrice(price);
        dish.setImageUrl("https://example.com/images/dish.jpg");
        dish.setIsActive(true);
        return dish;
    }

    @Test
    void shouldCreateDishSuccessfully() {
        when(restaurantServicePort.existsRestaurantByOwner(123L, 123L)).thenReturn(true);
        when(dishPersistencePort.createDish(any(Dish.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Dish dish = createDish(25000.0);

        Dish newDish = dishUseCase.createDish(dish, 123L);

        assertThat(newDish.getId()).isEqualTo(dish.getId());
        assertThat(newDish.getCategory().getName()).isEqualTo("Dish category name");
        assertThat(newDish.getRestaurant().getName()).isEqualTo("Restaurant name");
        assertThat(newDish.getPrice()).isEqualTo(25000.0);
        assertThat(newDish.getIsActive()).isTrue();
    }

    @Test
    void shouldFailToCreateDishWhenPriceIsNotPositive() {
        when(restaurantServicePort.existsRestaurantByOwner(123L, 123L)).thenReturn(true);

        Dish dish = createDish(-100.0);

        assertThatThrownBy(() -> dishUseCase.createDish(dish, 123L))
                .isInstanceOf(DishNonPositivePriceException.class)
                .hasMessageContaining("-100");
    }

    @Test
    void shouldCreateDishAsActiveByDefault() {
        when(restaurantServicePort.existsRestaurantByOwner(123L, 123L)).thenReturn(true);
        when(dishPersistencePort.createDish(any(Dish.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Dish dish = createDish(100.0);

        Dish newDish = dishUseCase.createDish(dish, 123L);

        assertThat(newDish.getIsActive()).isTrue();
    }

    @Test
    void shouldCallPersistMethodWhenSavingDish() {
        when(restaurantServicePort.existsRestaurantByOwner(123L, 123L)).thenReturn(true);
        when(dishPersistencePort.createDish(any(Dish.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Dish dish = createDish(100.0);

        dishUseCase.createDish(dish, 123L);

        verify(dishPersistencePort).createDish(dish);
    }

    @Test
    void shouldUpdateDishSuccessfully() {
        when(dishPersistencePort.existsDishByIdAndRestaurantOwner(1, 123L)).thenReturn(true);

        Dish dish = createDish(15000.0);
        dish.setDescription("New description");

        when(dishPersistencePort.updateDish(1, 15000.0, "New description"))
                .thenReturn(dish);

        Dish newDish = dishUseCase.updateDish(1, 15000.0, "New description", 123L);

        assertThat(newDish.getDescription()).isEqualTo("New description");
        assertThat(newDish.getPrice()).isEqualTo(15000.0);
    }

    @Test
    void shouldFailToUpdateDishWhenPriceIsNotPositive() {
        when(dishPersistencePort.existsDishByIdAndRestaurantOwner(1, 123L)).thenReturn(true);

        assertThatThrownBy(() -> dishUseCase.updateDish(1, -10.0, "Description", 123L))
                .isInstanceOf(DishNonPositivePriceException.class)
                .hasMessageContaining("-10");
    }

    @Test
    void shouldFailToUpdateDishWhenOwnerNotAuthorized() {
        when(dishPersistencePort.existsDishByIdAndRestaurantOwner(1, 123L)).thenReturn(false);

        assertThatThrownBy(() -> dishUseCase.updateDish(1, 100.0, "Description", 123L))
                .isInstanceOf(OwnerNotAuthorizedForRestaurantException.class);
    }

    @Test
    void shouldUpdateDishStatusSuccessfully() {
        when(dishPersistencePort.existsDishByIdAndRestaurantOwner(1, 123L)).thenReturn(true);

        Dish dish = createDish(100.0);
        dish.setIsActive(false);

        when(dishPersistencePort.updateDishStatus(1, false)).thenReturn(dish);

        Dish updatedDish = dishUseCase.updateDishStatus(1, false, 123L);

        assertThat(updatedDish.getIsActive()).isFalse();
    }

    @Test
    void shouldFailToUpdateDishStatusWhenOwnerNotAuthorized() {
        when(dishPersistencePort.existsDishByIdAndRestaurantOwner(1, 123L)).thenReturn(false);

        assertThatThrownBy(() -> dishUseCase.updateDishStatus(1, false, 123L))
                .isInstanceOf(OwnerNotAuthorizedForRestaurantException.class);
    }

    @Test
    void shouldReturnPaginatedRestaurantsWhenParametersAreValid() {
        Pagination<Dish> pagination = new Pagination<>();
        Dish r1 = createDish(123D);
        Dish r2 = createDish(123D);
        pagination.setItems(Arrays.asList(r1, r2));
        pagination.setCurrentPageNumber(1);
        pagination.setCurrentItemCount(2);
        pagination.setTotalItems(2);
        pagination.setTotalPages(1);
        pagination.setPageSize(10);

        when(dishPersistencePort.getPaginatedByCategoryIdSortedByName(1, 10, Optional.of(12)))
                .thenReturn(pagination);

        Pagination<Dish> result = dishUseCase.getPaginatedByCategoryIdSortedByName(1, 10, Optional.of(12));

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(2);

        verify(dishPersistencePort).getPaginatedByCategoryIdSortedByName(1, 10, Optional.of(12));
    }

    @Test
    void shouldThrowExceptionWhenPageIsLessThanOne() {
        assertThatThrownBy(() -> dishUseCase.getPaginatedByCategoryIdSortedByName(0, 10, Optional.empty()))
                .isInstanceOf(InvalidPaginationParameterException.class)
                .hasMessageContaining("Page must be greater than or equal to 1");

        verify(dishPersistencePort, never()).getPaginatedByCategoryIdSortedByName(anyInt(), anyInt(), any());
    }

    @Test
    void shouldThrowExceptionWhenSizeIsGreaterThan100() {
        assertThatThrownBy(() -> dishUseCase.getPaginatedByCategoryIdSortedByName(1, 101, Optional.empty()))
                .isInstanceOf(InvalidPaginationParameterException.class)
                .hasMessageContaining("Page size must be less than or equal to 100");

        verify(dishPersistencePort, never()).getPaginatedByCategoryIdSortedByName(anyInt(), anyInt(), any());
    }

    @Test
    void shouldReturnPaginatedDishesWhenCategoryIdIsEmpty() {
        Pagination<Dish> pagination = new Pagination<>();
        Dish r1 = createDish(123D);
        Dish r2 = createDish(123D);
        r1.setName("Alpha");
        r2.setName("Beta");
        pagination.setItems(Arrays.asList(r1, r2));
        pagination.setCurrentPageNumber(1);
        pagination.setCurrentItemCount(2);
        pagination.setTotalItems(2);
        pagination.setTotalPages(1);
        pagination.setPageSize(10);

        when(dishPersistencePort.getPaginatedByCategoryIdSortedByName(1, 10, Optional.empty()))
                .thenReturn(pagination);

        Pagination<Dish> result = dishUseCase.getPaginatedByCategoryIdSortedByName(1, 10, Optional.empty());

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems()).extracting(Dish::getName)
                .containsExactly("Alpha", "Beta");

        verify(dishPersistencePort).getPaginatedByCategoryIdSortedByName(1, 10, Optional.empty());
    }
}

