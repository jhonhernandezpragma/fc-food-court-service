package com.pragma.fc.food_curt.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DishCategoryTest {

    @Test
    void shouldCreateDishCategoryWithAllFields() {
        DishCategory dishCategory = new DishCategory(123, "Category name");

        assertThat(dishCategory.getId()).isEqualTo(123);
        assertThat(dishCategory.getName()).isEqualTo("Category name");
    }
}


