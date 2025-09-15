package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.api.IOrderServicePort;
import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.exception.ActiveOrderAlreadyExistsException;
import com.pragma.fc.food_curt.domain.exception.DishesFromDifferentRestaurantsException;
import com.pragma.fc.food_curt.domain.exception.DuplicateDishIdException;
import com.pragma.fc.food_curt.domain.exception.InvalidOrderStatusForAssignmentException;
import com.pragma.fc.food_curt.domain.exception.InvalidPaginationParameterException;
import com.pragma.fc.food_curt.domain.exception.OrderAlreadyAssignedException;
import com.pragma.fc.food_curt.domain.exception.OrderNotInPreparationException;
import com.pragma.fc.food_curt.domain.exception.OrderNotInReadyException;
import com.pragma.fc.food_curt.domain.exception.OrderOtpInvalidException;
import com.pragma.fc.food_curt.domain.exception.OrderWorkerMismatchException;
import com.pragma.fc.food_curt.domain.exception.WorkerRestaurantMismatchException;
import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.OrderOtp;
import com.pragma.fc.food_curt.domain.model.OrderStatus;
import com.pragma.fc.food_curt.domain.model.Pagination;
import com.pragma.fc.food_curt.domain.spi.INotificationClientPort;
import com.pragma.fc.food_curt.domain.spi.IOrderPersistencePort;
import com.pragma.fc.food_curt.domain.spi.IOtpServicePort;
import com.pragma.fc.food_curt.domain.spi.IUserClientPort;
import com.pragma.fc.food_curt.infraestructure.exception.InvalidRestaurantOrderException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OrderUseCase implements IOrderServicePort {
    private final IDishServicePort dishServicePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final INotificationClientPort notificationClientPort;
    private final IOtpServicePort otpServicePort;
    private final Integer otpExpirationMinutes;
    private final IUserClientPort userClientPort;

    public OrderUseCase(IDishServicePort dishServicePort, IOrderPersistencePort orderPersistencePort, IRestaurantServicePort restaurantServicePort, INotificationClientPort notificationClientPort, IOtpServicePort otpServicePort, Integer otpExpirationMinutes, IUserClientPort userClientPort) {
        this.dishServicePort = dishServicePort;
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantServicePort = restaurantServicePort;
        this.notificationClientPort = notificationClientPort;
        this.otpServicePort = otpServicePort;
        this.otpExpirationMinutes = otpExpirationMinutes;
        this.userClientPort = userClientPort;
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

    @Override
    public Pagination<Order> getPaginatedByStatusSortedByDate(int page, int size, Optional<Integer> orderStatusId, Long workerDocumentNumber) {
        if (page < 1) {
            throw new InvalidPaginationParameterException("Page must be greater than or equal to 1");
        }

        if (size > 100) {
            throw new InvalidPaginationParameterException("Page size must be less than or equal to 100");
        }

        Long restaurantNit = restaurantServicePort.getRestaurantNitByWorker(workerDocumentNumber);

        return orderPersistencePort.getPaginatedByStatusSortedByDate(page, size, orderStatusId, restaurantNit);
    }

    @Override
    public Order assignWorkerToOrder(Integer orderId, Long workerDocumentNumber) {
        Long myRestaurantNit = restaurantServicePort.getRestaurantNitByWorker(workerDocumentNumber);
        Order order = orderPersistencePort.getById(orderId);

        if (myRestaurantNit == null
                || order.getRestaurant().getNit() == null
                || !myRestaurantNit.equals(order.getRestaurant().getNit())
        ) {
            throw new WorkerRestaurantMismatchException(orderId, workerDocumentNumber);
        }

        if (order.getWorkerDocumentNumber() != null) {
            throw new OrderAlreadyAssignedException(orderId, order.getWorkerDocumentNumber());
        }

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new InvalidOrderStatusForAssignmentException(orderId, order.getStatus().name());
        }

        order.setStatus(OrderStatus.IN_PREPARATION);
        order.setWorkerDocumentNumber(workerDocumentNumber);

        return orderPersistencePort.updateOrder(order);
    }

    @Override
    public Order markAsReady(Integer orderId, Long workerDocumentNumber) {
        Order order = orderPersistencePort.getById(orderId);

        if (!order.getWorkerDocumentNumber().equals(workerDocumentNumber)) {
            throw new OrderWorkerMismatchException(orderId);
        }

        if (!order.getStatus().equals(OrderStatus.IN_PREPARATION)) {
            throw new OrderNotInPreparationException(orderId, order.getStatus().name());
        }

        order.setStatus(OrderStatus.READY);

        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(otpExpirationMinutes);
        String otpCode = otpServicePort.generateOtp();

        OrderOtp orderOtp = new OrderOtp(
                null,
                orderId,
                otpCode,
                LocalDateTime.now(),
                expirationDate,
                false
        );

        orderPersistencePort.addOtpCode(orderOtp);

        Order orderUpdated = orderPersistencePort.updateOrder(order);

        Long customerDocumentNumber = order.getCustomerDocumentNumber();
        String customerPhoneNumber = userClientPort.getPhoneNumberByDocumentNumber(customerDocumentNumber);

        if (customerPhoneNumber != null) {
            notificationClientPort.sendOrderReadyNotification(customerPhoneNumber, orderId.toString(), otpCode);
        }

        return orderUpdated;
    }

    @Override
    public Order finishOrder(Integer orderId, Long workerDocumentNumber, String otpCode) {
        Order order = orderPersistencePort.getById(orderId);

        if (!order.getWorkerDocumentNumber().equals(workerDocumentNumber)) {
            throw new OrderWorkerMismatchException(orderId);
        }

        if (!order.getStatus().equals(OrderStatus.READY)) {
            throw new OrderNotInReadyException(orderId, order.getStatus().name());
        }

        OrderOtp orderOtp = orderPersistencePort.getLastOtpByOrderId(order.getOrderId());

        if (orderOtp == null || orderOtp.getUsed() || !orderOtp.getCode().equals(otpCode)) {
            throw new OrderOtpInvalidException();
        }

        orderOtp.setUsed(true);
        orderPersistencePort.updateOtp(orderOtp);

        order.setStatus(OrderStatus.DELIVERED);
        return orderPersistencePort.updateOrder(order);
    }
}
