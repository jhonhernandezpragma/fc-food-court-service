package com.pragma.fc.food_curt.domain.spi;

import com.pragma.fc.food_curt.domain.model.Order;

public interface IOrderPersistencePort {
    Order createOrder(Order order);

    boolean existsByCustomerDocumentNumber(Long customerDocumentNumber);
}
