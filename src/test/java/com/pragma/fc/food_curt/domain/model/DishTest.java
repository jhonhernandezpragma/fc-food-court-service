package com.pragma.fc.food_curt.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DishTest {
    @Test
    void shouldCreateDishUsingAllArgsConstructor() {
        DishCategory category = new DishCategory(1, "Dish category name");
        Restaurant restaurant = new Restaurant(
                123L,
                213L,
                "Restaurant name",
                "Restaurant address",
                "Restaurant phone",
                "Restaurant logo url"
        );

        Dish dish = new Dish(
                10,
                category,
                restaurant,
                "Delicious Dish",
                "A tasty and healthy dish description",
                25000.0,
                "https://example.com/images/dish.jpg",
                true
        );

        assertThat(dish.getId()).isEqualTo(10);
        assertThat(dish.getCategory().getName()).isEqualTo("Dish category name");
        assertThat(dish.getRestaurant().getName()).isEqualTo("Restaurant name");
        assertThat(dish.getName()).isEqualTo("Delicious Dish");
        assertThat(dish.getDescription()).isEqualTo("A tasty and healthy dish description");
        assertThat(dish.getPrice()).isEqualTo(25000.0);
        assertThat(dish.getImageUrl()).isEqualTo("https://example.com/images/dish.jpg");
        assertThat(dish.getIsActive()).isTrue();
    }

    @Test
    void shouldCreateDishWithAllFields() {
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
        dish.setPrice(25000.0);
        dish.setImageUrl("https://example.com/images/dish.jpg");
        dish.setIsActive(true);

        assertThat(dish.getId()).isEqualTo(10);

        assertThat(dish.getCategory()).isNotNull();
        assertThat(dish.getCategory().getId()).isEqualTo(1);
        assertThat(dish.getCategory().getName()).isEqualTo("Dish category name");

        assertThat(dish.getRestaurant()).isNotNull();
        assertThat(dish.getRestaurant().getNit()).isEqualTo(123L);
        assertThat(dish.getRestaurant().getOwnerDocumentNumber()).isEqualTo(213L);
        assertThat(dish.getRestaurant().getName()).isEqualTo("Restaurant name");
        assertThat(dish.getRestaurant().getAddress()).isEqualTo("Restaurant address");
        assertThat(dish.getRestaurant().getPhone()).isEqualTo("Restaurant phone");
        assertThat(dish.getRestaurant().getLogoUrl()).isEqualTo("Restaurant logo url");

        assertThat(dish.getName()).isEqualTo("Delicious Dish");
        assertThat(dish.getDescription()).isEqualTo("A tasty and healthy dish description");
        assertThat(dish.getPrice()).isEqualTo(25000.0);
        assertThat(dish.getImageUrl()).isEqualTo("https://example.com/images/dish.jpg");
        assertThat(dish.getIsActive()).isTrue();
    }
}
