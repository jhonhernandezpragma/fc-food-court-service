package com.pragma.fc.food_curt.infraestructure.out.jpa.mapper;

import com.pragma.fc.food_curt.domain.model.Order;
import com.pragma.fc.food_curt.domain.model.OrderItem;
import com.pragma.fc.food_curt.domain.model.OrderStatus;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderDishEntity;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderDishId;
import com.pragma.fc.food_curt.infraestructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {
    default OrderEntity toEntity(Order order, List<OrderItem> orderItems, IDishEntityMapper dishMapper, IRestaurantEntityMapper restaurantMapper) {
        if (order == null) return null;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setRestaurant(restaurantMapper.toEntity(order.getRestaurant()));
        orderEntity.setCustomerDocumentNumber(order.getCustomerDocumentNumber());
        orderEntity.setCreatedAt(order.getCreatedAt());

        Set<OrderDishEntity> orderDishEntities = orderItems
                .stream()
                .map(item -> {
                    OrderDishEntity orderDishEntity = new OrderDishEntity();
                    orderDishEntity.setId(new OrderDishId());
                    orderDishEntity.setDish((dishMapper.toEntity(item.getDish())));
                    orderDishEntity.setPrice(item.getDish().getPrice());
                    orderDishEntity.setQuantity(item.getQuantity());
                    orderDishEntity.setOrder(orderEntity);
                    return orderDishEntity;
                })
                .collect(Collectors.toSet());

        orderEntity.setOrderDishes(orderDishEntities);
        return orderEntity;
    }

    default Order toModel(OrderEntity entity, IDishEntityMapper dishMapper, IRestaurantEntityMapper restaurantMapper) {
        if (entity == null) return null;

        Order order = new Order();
        order.setOrderId(entity.getOrderId());
        order.setRestaurant(restaurantMapper.toModel(entity.getRestaurant()));
        order.setStatus(OrderStatus.valueOf(entity.getStatus().getName()));
        order.setCustomerDocumentNumber(order.getCustomerDocumentNumber());

        if (entity.getOrderDishes() != null) {
            List<OrderItem> items = entity.getOrderDishes().stream()
                    .map(od -> {
                        OrderItem item = new OrderItem();
                        item.setDish(dishMapper.toModel(od.getDish()));
                        item.setPrice(od.getPrice());
                        item.setQuantity(od.getQuantity());
                        return item;
                    })
                    .toList();
            order.setItems(items);
        }

        order.setCreatedAt(entity.getCreatedAt());
        return order;
    }
}
