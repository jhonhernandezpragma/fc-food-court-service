package com.pragma.fc.food_curt.infraestructure.out.jpa.adapter;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.OrderItem;
import com.pragma.fc.food_curt.domain.model.OrderOtp;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.spi.IOrderPersistencePort;
import com.pragma.fc.food_curt.infraestructure.exception.DishNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.OrderNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.OrderOtpNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.OrderStatusNotFoundException;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderOtpEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderStatusEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IOrderOtpEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IOrderOtpRepository;
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
    private final IOrderOtpRepository orderOtpRepository;
    private final IOrderOtpEntityMapper otpEntityMapper;

    public OrderJpaAdapter(
            IOrderRepository orderRepository,
            IDishServicePort dishServicePort,
            IOrderEntityMapper orderEntityMapper,
            IDishEntityMapper dishEntityMapper,
            IRestaurantEntityMapper restaurantEntityMapper,
            IOrderStatusRepository orderStatusRepository,
            EntityManager entityManager,
            IOrderOtpRepository orderOtpRepository,
            IOrderOtpEntityMapper otpEntityMapper
    ) {
        this.orderRepository = orderRepository;
        this.dishServicePort = dishServicePort;
        this.orderEntityMapper = orderEntityMapper;
        this.dishEntityMapper = dishEntityMapper;
        this.restaurantEntityMapper = restaurantEntityMapper;
        this.orderStatusRepository = orderStatusRepository;
        this.entityManager = entityManager;
        this.orderOtpRepository = orderOtpRepository;
        this.otpEntityMapper = otpEntityMapper;
    }

    @Override
    public Order createOrder(Order order) {
        return storeOrder(order);
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

    @Override
    public Order getById(Integer orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        return orderEntityMapper.toModel(orderEntity, dishEntityMapper, restaurantEntityMapper);
    }

    @Override
    public Order updateOrder(Order order) {
        OrderEntity existingOrder = orderRepository.findById(order.getOrderId())
                .orElseThrow(OrderNotFoundException::new);
        return storeOrder(order);
    }

    @Override
    public OrderOtp addOtpCode(OrderOtp orderOtp) {
        OrderOtpEntity entity = otpEntityMapper.toEntity(orderOtp);
        OrderOtpEntity newOtp = orderOtpRepository.save(entity);
        return otpEntityMapper.toModel(newOtp);
    }

    @Override
    public OrderOtp updateOtp(OrderOtp orderOtp) {
        OrderOtpEntity entity = otpEntityMapper.toEntity(orderOtp);
        OrderOtpEntity updatedOtp = orderOtpRepository.save(entity);
        return otpEntityMapper.toModel(updatedOtp);
    }

    @Override
    public OrderOtp getLastOtpByOrderId(Integer orderId) {
        OrderOtpEntity orderOtpEntity = orderOtpRepository.findFirstByOrderIdOrderByCreatedAtDesc(orderId)
                .orElseThrow(() -> new OrderOtpNotFoundException(orderId));
        return otpEntityMapper.toModel(orderOtpEntity);
    }

    private Order storeOrder(Order order) {
        List<Integer> dishIds = order.getItems()
                .stream()
                .map(item -> item.getDish().getId())
                .toList();

        Map<Integer, Integer> quantityMap = order.getItems()
                .stream()
                .collect(Collectors.toMap(item -> item.getDish().getId(), OrderItem::getQuantity));

        // TODO: move to use case
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
}
