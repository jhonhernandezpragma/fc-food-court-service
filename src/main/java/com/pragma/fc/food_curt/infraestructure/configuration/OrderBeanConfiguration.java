package com.pragma.fc.food_curt.infraestructure.configuration;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.api.IOrderServicePort;
import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.spi.IOrderPersistencePort;
import com.pragma.fc.food_curt.domain.usecase.OrderUseCase;
import com.pragma.fc.food_curt.infraestructure.out.jpa.adapter.OrderJpaAdapter;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IOrderRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IOrderStatusRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderBeanConfiguration {
    private final IOrderRepository orderRepository;
    private final IDishServicePort dishServicePort;
    private final IOrderEntityMapper orderEntityMapper;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IOrderStatusRepository orderStatusRepository;
    private final EntityManager entityManager;
    private final IRestaurantServicePort restaurantServicePort;

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(dishServicePort, orderPersistencePort(), restaurantServicePort);
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, dishServicePort, orderEntityMapper, dishEntityMapper, restaurantEntityMapper, orderStatusRepository, entityManager);
    }
}
