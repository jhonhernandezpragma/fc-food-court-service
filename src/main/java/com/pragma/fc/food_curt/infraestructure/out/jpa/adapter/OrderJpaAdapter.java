package com.pragma.fc.food_curt.infraestructure.out.jpa.adapter;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.OrderItem;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.spi.IOrderPersistencePort;
import com.pragma.fc.food_curt.infraestructure.exception.DishNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.OrderStatusNotFoundException;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderStatusEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IOrderRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IOrderStatusRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderJpaAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;
    private final IDishServicePort dishServicePort;
    private final IOrderEntityMapper orderEntityMapper;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IOrderStatusRepository orderStatusRepository;
    private final EntityManager entityManager;


    public OrderJpaAdapter(
            IOrderRepository orderRepository,
            IDishServicePort dishServicePort,
            IOrderEntityMapper orderEntityMapper,
            IDishEntityMapper dishEntityMapper,
            IRestaurantEntityMapper restaurantEntityMapper,
            IOrderStatusRepository orderStatusRepository,
            EntityManager entityManager
    ) {
        this.orderRepository = orderRepository;
        this.dishServicePort = dishServicePort;
        this.orderEntityMapper = orderEntityMapper;
        this.dishEntityMapper = dishEntityMapper;
        this.restaurantEntityMapper = restaurantEntityMapper;
        this.orderStatusRepository = orderStatusRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Order createOrder(Order order) {
        List<Integer> dishIds = order.getItems()
                .stream()
                .map(item -> item.getDish().getId())
                .toList();

        Map<Integer, Integer> quantityMap = order.getItems()
                .stream()
                .collect(Collectors.toMap(item -> item.getDish().getId(), OrderItem::getQuantity));

        List<OrderItem> orderItems = dishServicePort.getAllByIds(dishIds)
                .stream()
                .map(dish -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setDish(dish);
                    orderItem.setQuantity(quantityMap.get(dish.getId()));
                    return orderItem;
                })
                .toList();

        if (orderItems.size() != order.getItems().size()) {
            throw new DishNotFoundException();
        }

        OrderStatusEntity orderStatusEntity = orderStatusRepository.findByName(order.getStatus().name())
                .orElseThrow(OrderStatusNotFoundException::new);

        OrderEntity orderEntity = orderEntityMapper.toEntity(order, orderItems, dishEntityMapper, restaurantEntityMapper);
        orderEntity.setStatus(orderStatusEntity);

        OrderEntity newOrderEntity = entityManager.merge(orderEntity);

        return orderEntityMapper.toModel(newOrderEntity, dishEntityMapper, restaurantEntityMapper);
    }

    @Override
    public boolean existsByCustomerDocumentNumber(Long customerDocumentNumber) {
        return orderRepository.existsByCustomerDocumentNumber(customerDocumentNumber);
    }

    @Override
    public Pagination<Order> getPaginatedByStatusSortedByDate(int page, int size, Optional<Integer> orderStatusId, Long restaurantNit) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<OrderEntity> orderEntityPage = orderStatusId
                .map(statusId -> orderRepository.findByStatusIdAndRestaurantNit(statusId, restaurantNit, pageable))
                .orElseGet(() -> orderRepository.findAllByRestaurantNit(restaurantNit, pageable));

        Pagination<Order> pagination = new Pagination();
        pagination.setItems(orderEntityPage
                .stream()
                .map(entity -> orderEntityMapper.toModel(entity, dishEntityMapper, restaurantEntityMapper))
                .toList());

        pagination.setPageSize(size);
        pagination.setTotalItems(orderEntityPage.getTotalElements());
        pagination.setTotalPages(orderEntityPage.getTotalPages());

        pagination.setCurrentItemCount(orderEntityPage.getNumberOfElements());
        pagination.setFirstPage(orderEntityPage.isFirst());
        pagination.setLastPage(orderEntityPage.isLast());

        return pagination;
    }
}
