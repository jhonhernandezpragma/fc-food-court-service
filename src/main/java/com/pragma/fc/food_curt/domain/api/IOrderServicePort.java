package com.pragma.fc.food_curt.domain.api;

import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.Pagination;

import java.util.Optional;

public interface IOrderServicePort {
    Order createOrder(Order order, Long customerDocumentNumber);

    Pagination<Order> getPaginatedByStatusSortedByDate(int page, int size, Optional<Integer> orderStatusId, Long workerDocumentNumber);
}
