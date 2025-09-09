package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.api.IOrderServicePort;
import com.pragma.fc.food_curt.domain.exception.ActiveOrderAlreadyExistsException;
import com.pragma.fc.food_curt.domain.exception.DishesFromDifferentRestaurantsException;
import com.pragma.fc.food_curt.domain.exception.DuplicateDishIdException;
import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.OrderStatus;
import com.pragma.fc.food_curt.domain.spi.IOrderPersistencePort;
import com.pragma.fc.food_curt.infraestructure.exception.InvalidRestaurantOrderException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class OrderUseCase implements IOrderServicePort {
    private final IDishServicePort dishServicePort;
    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IDishServicePort dishServicePort, IOrderPersistencePort orderPersistencePort) {
        this.dishServicePort = dishServicePort;
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public Order createOrder(Order order, Long customerDocumentNumber) {
        List<Integer> dishIds = order.getItems()
                .stream()
                .map(item -> item.getDish().getId())
                .toList();

        Set<Integer> uniqueIds = Set.copyOf(dishIds);
        if (uniqueIds.size() != dishIds.size()) {
            throw new DuplicateDishIdException();
        }

        if (!dishServicePort.allBelongToSameRestaurant(dishIds)) {
            throw new DishesFromDifferentRestaurantsException();
        }

        if (!dishServicePort.belongToRestaurant(dishIds.getFirst(), order.getRestaurant().getNit())) {
            throw new InvalidRestaurantOrderException();
        }

        if (orderPersistencePort.existsByCustomerDocumentNumber(customerDocumentNumber)) {
            throw new ActiveOrderAlreadyExistsException(customerDocumentNumber);
        }

        order.setStatus(OrderStatus.PENDING);
        order.setCustomerDocumentNumber(customerDocumentNumber);
        order.setCreatedAt(LocalDateTime.now());
        return orderPersistencePort.createOrder(order);
    }
}
