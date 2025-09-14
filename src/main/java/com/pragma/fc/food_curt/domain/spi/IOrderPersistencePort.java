package com.pragma.fc.food_curt.domain.spi;

import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.OrderOtp;
import com.pragma.fc.food_curt.domain.model.Pagination;

import java.util.Optional;

public interface IOrderPersistencePort {
    Order createOrder(Order order);

    boolean existsByCustomerDocumentNumber(Long customerDocumentNumber);

    Pagination<Order> getPaginatedByStatusSortedByDate(int page, int size, Optional<Integer> orderStatusId, Long restaurantNit);

    Order getById(Integer orderId);

    Order updateOrder(Order order);

    void addOtpCode(OrderOtp orderOtp);
}
