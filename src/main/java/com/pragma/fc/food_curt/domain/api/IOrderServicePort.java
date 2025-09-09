package com.pragma.fc.food_curt.domain.api;

import com.pragma.fc.food_curt.domain.model.Order;

public interface IOrderServicePort {
    Order createOrder(Order order, Long customerDocumentNumber);
}
