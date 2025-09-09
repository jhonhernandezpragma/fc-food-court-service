package com.pragma.fc.food_curt.domain.usecase;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.exception.ActiveOrderAlreadyExistsException;
import com.pragma.fc.food_curt.domain.exception.DishesFromDifferentRestaurantsException;
import com.pragma.fc.food_curt.domain.exception.DuplicateDishIdException;
import com.pragma.fc.food_curt.domain.model.Dish;
import com.pragma.fc.food_curt.domain.model.DishCategory;
import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.OrderItem;
import com.pragma.fc.food_curt.domain.model.OrderStatus;
import com.pragma.fc.food_curt.domain.model.Restaurant;
import com.pragma.fc.food_curt.domain.spi.IOrderPersistencePort;
import com.pragma.fc.food_curt.infraestructure.exception.InvalidRestaurantOrderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @InjectMocks
    OrderUseCase orderUseCase;

    @Mock
    IDishServicePort dishServicePort;

    @Mock
    IOrderPersistencePort orderPersistencePort;

    private Restaurant createRestaurant() {
        return new Restaurant(
                123L,
                456L,
                "Test Restaurant",
                "Test Address",
                "123-456-7890",
                "logo.png"
        );
    }

    private DishCategory createDishCategory() {
        return new DishCategory(1, "Main Course");
    }

    private Dish createDish(Integer id, String name, Double price) {
        return new Dish(
                id,
                createDishCategory(),
                createRestaurant(),
                name,
                "Test description",
                price,
                "dish.png",
                true
        );
    }

    private OrderItem createOrderItem(Integer dishId, String dishName, Double price, Integer quantity) {
        OrderItem item = new OrderItem();
        item.setDish(createDish(dishId, dishName, price));
        item.setQuantity(quantity);
        return item;
    }

    private Order createOrder(List<OrderItem> items) {
        Order order = new Order();
        order.setRestaurant(createRestaurant());
        order.setItems(items);
        return order;
    }

    @Test
    void shouldCreateOrderSuccessfully() {

        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(
                createOrderItem(1, "Burger", 15.0, 2),
                createOrderItem(2, "Fries", 8.0, 1)
        );
        Order order = createOrder(items);
        List<Integer> dishIds = Arrays.asList(1, 2);

        when(dishServicePort.allBelongToSameRestaurant(dishIds)).thenReturn(true);
        when(dishServicePort.belongToRestaurant(1, 123L)).thenReturn(true);
        when(orderPersistencePort.existsByCustomerDocumentNumber(customerDocumentNumber)).thenReturn(false);
        when(orderPersistencePort.createOrder(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setOrderId(999);
            return savedOrder;
        });


        Order result = orderUseCase.createOrder(order, customerDocumentNumber);


        assertThat(result).isNotNull();
        assertThat(result.getOrderId()).isEqualTo(999);
        assertThat(result.getCustomerDocumentNumber()).isEqualTo(customerDocumentNumber);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getCreatedAt()).isNotNull();

        verify(orderPersistencePort).createOrder(any(Order.class));
    }

    @Test
    void shouldSetOrderPropertiesCorrectlyWhenCreating() {

        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(createOrderItem(1, "Burger", 15.0, 2));
        Order order = createOrder(items);
        List<Integer> dishIds = Arrays.asList(1);

        when(dishServicePort.allBelongToSameRestaurant(dishIds)).thenReturn(true);
        when(dishServicePort.belongToRestaurant(1, 123L)).thenReturn(true);
        when(orderPersistencePort.existsByCustomerDocumentNumber(customerDocumentNumber)).thenReturn(false);
        when(orderPersistencePort.createOrder(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Order result = orderUseCase.createOrder(order, customerDocumentNumber);


        assertThat(result.getCustomerDocumentNumber()).isEqualTo(customerDocumentNumber);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.getCreatedAt()).isAfter(LocalDateTime.now().minusMinutes(1));
    }

    @Test
    void shouldThrowDuplicateDishIdExceptionWhenOrderHasDuplicatedDishes() {

        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(
                createOrderItem(1, "Burger", 15.0, 2),
                createOrderItem(1, "Burger", 15.0, 1)
        );
        Order order = createOrder(items);


        assertThatThrownBy(() -> orderUseCase.createOrder(order, customerDocumentNumber))
                .isInstanceOf(DuplicateDishIdException.class);

        verify(dishServicePort, never()).allBelongToSameRestaurant(anyList());
        verify(orderPersistencePort, never()).createOrder(any(Order.class));
    }

    @Test
    void shouldThrowDishesFromDifferentRestaurantsExceptionWhenDishesFromDifferentRestaurants() {

        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(
                createOrderItem(1, "Burger", 15.0, 2),
                createOrderItem(2, "Pizza", 20.0, 1)
        );
        Order order = createOrder(items);
        List<Integer> dishIds = Arrays.asList(1, 2);

        when(dishServicePort.allBelongToSameRestaurant(dishIds)).thenReturn(false);


        assertThatThrownBy(() -> orderUseCase.createOrder(order, customerDocumentNumber))
                .isInstanceOf(DishesFromDifferentRestaurantsException.class);

        verify(dishServicePort).allBelongToSameRestaurant(dishIds);
        verify(orderPersistencePort, never()).createOrder(any(Order.class));
    }

    @Test
    void shouldThrowInvalidRestaurantOrderExceptionWhenDishDoesNotBelongToRestaurant() {

        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(createOrderItem(1, "Burger", 15.0, 2));
        Order order = createOrder(items);
        List<Integer> dishIds = Arrays.asList(1);

        when(dishServicePort.allBelongToSameRestaurant(dishIds)).thenReturn(true);
        when(dishServicePort.belongToRestaurant(1, 123L)).thenReturn(false);


        assertThatThrownBy(() -> orderUseCase.createOrder(order, customerDocumentNumber))
                .isInstanceOf(InvalidRestaurantOrderException.class);

        verify(dishServicePort).allBelongToSameRestaurant(dishIds);
        verify(dishServicePort).belongToRestaurant(1, 123L);
        verify(orderPersistencePort, never()).createOrder(any(Order.class));
    }

    @Test
    void shouldThrowActiveOrderAlreadyExistsExceptionWhenCustomerHasActiveOrder() {

        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(createOrderItem(1, "Burger", 15.0, 2));
        Order order = createOrder(items);
        List<Integer> dishIds = Arrays.asList(1);

        when(dishServicePort.allBelongToSameRestaurant(dishIds)).thenReturn(true);
        when(dishServicePort.belongToRestaurant(1, 123L)).thenReturn(true);
        when(orderPersistencePort.existsByCustomerDocumentNumber(customerDocumentNumber)).thenReturn(true);


        assertThatThrownBy(() -> orderUseCase.createOrder(order, customerDocumentNumber))
                .isInstanceOf(ActiveOrderAlreadyExistsException.class)
                .hasMessageContaining(customerDocumentNumber.toString());

        verify(dishServicePort).allBelongToSameRestaurant(dishIds);
        verify(dishServicePort).belongToRestaurant(1, 123L);
        verify(orderPersistencePort).existsByCustomerDocumentNumber(customerDocumentNumber);
        verify(orderPersistencePort, never()).createOrder(any(Order.class));
    }

    @Test
    void shouldCallAllValidationServicesInCorrectOrder() {

        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(
                createOrderItem(1, "Burger", 15.0, 2),
                createOrderItem(2, "Fries", 8.0, 1)
        );
        Order order = createOrder(items);
        List<Integer> dishIds = Arrays.asList(1, 2);

        when(dishServicePort.allBelongToSameRestaurant(dishIds)).thenReturn(true);
        when(dishServicePort.belongToRestaurant(1, 123L)).thenReturn(true);
        when(orderPersistencePort.existsByCustomerDocumentNumber(customerDocumentNumber)).thenReturn(false);
        when(orderPersistencePort.createOrder(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));


        orderUseCase.createOrder(order, customerDocumentNumber);


        verify(dishServicePort).allBelongToSameRestaurant(dishIds);
        verify(dishServicePort).belongToRestaurant(1, 123L);
        verify(orderPersistencePort).existsByCustomerDocumentNumber(customerDocumentNumber);
        verify(orderPersistencePort).createOrder(any(Order.class));
    }

    @Test
    void shouldHandleOrderWithSingleItem() {

        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(createOrderItem(1, "Burger", 15.0, 1));
        Order order = createOrder(items);
        List<Integer> dishIds = Arrays.asList(1);

        when(dishServicePort.allBelongToSameRestaurant(dishIds)).thenReturn(true);
        when(dishServicePort.belongToRestaurant(1, 123L)).thenReturn(true);
        when(orderPersistencePort.existsByCustomerDocumentNumber(customerDocumentNumber)).thenReturn(false);
        when(orderPersistencePort.createOrder(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Order result = orderUseCase.createOrder(order, customerDocumentNumber);


        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getCustomerDocumentNumber()).isEqualTo(customerDocumentNumber);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);

        verify(orderPersistencePort).createOrder(any(Order.class));
    }

    @Test
    void shouldHandleOrderWithMultipleQuantitiesOfSameDish() {
        Long customerDocumentNumber = 12345678L;
        List<OrderItem> items = Arrays.asList(createOrderItem(1, "Burger", 15.0, 5));
        Order order = createOrder(items);
        List<Integer> dishIds = Arrays.asList(1);

        when(dishServicePort.allBelongToSameRestaurant(dishIds)).thenReturn(true);
        when(dishServicePort.belongToRestaurant(1, 123L)).thenReturn(true);
        when(orderPersistencePort.existsByCustomerDocumentNumber(customerDocumentNumber)).thenReturn(false);
        when(orderPersistencePort.createOrder(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Order result = orderUseCase.createOrder(order, customerDocumentNumber);


        assertThat(result).isNotNull();
        assertThat(result.getItems().get(0).getQuantity()).isEqualTo(5);

        verify(orderPersistencePort).createOrder(any(Order.class));
    }
}
