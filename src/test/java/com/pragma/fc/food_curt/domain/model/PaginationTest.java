package com.pragma.fc.food_curt.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PaginationTest {

    @Test
    void shouldCreatePaginationUsingAllArgsConstructor() {
        List<String> items = Arrays.asList("Item1", "Item2", "Item3");

        Pagination<String> pagination = new Pagination<>(
                items,
                1,
                items.size(),
                false,
                true,
                3,
                9,
                3
        );

        assertThat(pagination.getItems()).hasSize(3);
        assertThat(pagination.getItems()).containsExactly("Item1", "Item2", "Item3");
        assertThat(pagination.getCurrentPageNumber()).isEqualTo(1);
        assertThat(pagination.getCurrentItemCount()).isEqualTo(3);
        assertThat(pagination.isFirstPage()).isTrue();
        assertThat(pagination.isLastPage()).isFalse();
        assertThat(pagination.getTotalPages()).isEqualTo(3);
        assertThat(pagination.getTotalItems()).isEqualTo(9L);
        assertThat(pagination.getPageSize()).isEqualTo(3);
    }

    @Test
    void shouldCreatePaginationWithSetters() {
        Pagination<String> pagination = new Pagination<>();
        pagination.setItems(Arrays.asList("A", "B"));
        pagination.setCurrentPageNumber(2);
        pagination.setCurrentItemCount(2);
        pagination.setFirstPage(false);
        pagination.setLastPage(true);
        pagination.setTotalPages(5);
        pagination.setTotalItems(10);
        pagination.setPageSize(2);

        assertThat(pagination.getItems()).hasSize(2);
        assertThat(pagination.getItems()).containsExactly("A", "B");
        assertThat(pagination.getCurrentPageNumber()).isEqualTo(2);
        assertThat(pagination.getCurrentItemCount()).isEqualTo(2);
        assertThat(pagination.isFirstPage()).isFalse();
        assertThat(pagination.isLastPage()).isTrue();
        assertThat(pagination.getTotalPages()).isEqualTo(5);
        assertThat(pagination.getTotalItems()).isEqualTo(10L);
        assertThat(pagination.getPageSize()).isEqualTo(2);
    }
}
