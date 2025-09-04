package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.exception.DishNonPositivePriceException;
import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.model.DishCategory;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.IDishPersistencePort;
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
class DishUseCaseTest {
    @InjectMocks
    DishUseCase dishUseCase;

    @Mock
    IDishPersistencePort dishPersistencePort;

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
        when(dishPersistencePort.createDish(any(Dish.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Dish dish = createDish(25000.0);

        Dish newDish = dishUseCase.createDish(dish);

        assertThat(newDish.getId()).isEqualTo(dish.getId());
        assertThat(newDish.getCategory().getName()).isEqualTo("Dish category name");
        assertThat(newDish.getRestaurant().getName()).isEqualTo("Restaurant name");
        assertThat(newDish.getName()).isEqualTo("Delicious Dish");
        assertThat(newDish.getDescription()).isEqualTo("A tasty and healthy dish description");
        assertThat(newDish.getPrice()).isEqualTo(25000.0);
        assertThat(newDish.getImageUrl()).isEqualTo("https://example.com/images/dish.jpg");
        assertThat(newDish.getIsActive()).isTrue();
    }

    @Test
    void shouldFailToCreateDishWhenPriceIsNotPositive() {
        Dish dish = createDish(-100.0);

        assertThatThrownBy(() -> dishUseCase.createDish(dish))
                .isInstanceOf(DishNonPositivePriceException.class)
                .hasMessageContaining("-100");
    }

    @Test
    void shouldCreateDishAsActiveByDefault() {
        when(dishPersistencePort.createDish(any(Dish.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Dish dish = createDish(100.0);

        Dish newDish = dishUseCase.createDish(dish);

        assertThat(newDish.getIsActive()).isTrue();
    }

    @Test
    void shouldCallPersistMethodWhenSavingDish() {
        when(dishPersistencePort.createDish(any(Dish.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Dish dish = createDish(100.0);

        dishUseCase.createDish(dish);

        verify(dishPersistencePort).createDish(dish);
    }

    @Test
    void shouldUpdateDishSuccessfully() {
        Dish dish = createDish(15000.0);
        dish.setDescription("New description");

        when(dishPersistencePort.updateDish(1, 15000.0, "New description"))
                .thenReturn(dish);


        Dish newDish = dishUseCase.updateDish(1, 15000.0, "New description" );

        assertThat(newDish.getId()).isEqualTo(dish.getId());
        assertThat(newDish.getDescription()).isEqualTo(dish.getDescription());
        assertThat(newDish.getPrice()).isEqualTo(dish.getPrice());
    }

    @Test
    void shouldFailToUpdateDishWhenPriceIsNotPositive() {
        assertThatThrownBy(() -> dishUseCase.updateDish(1, -10.0, "Description"))
                .isInstanceOf(DishNonPositivePriceException.class)
                .hasMessageContaining("-10");
    }
}
