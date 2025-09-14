package com.pragma.fc.food_curt.infraestructure.configuration;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.api.IOrderServicePort;
import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.spi.INotificationClientPort;
import com.pragma.fc.food_curt.domain.spi.IOrderPersistencePort;
import com.pragma.fc.food_curt.domain.spi.IOtpServicePort;
import com.pragma.fc.food_curt.domain.spi.IUserClientPort;
import com.pragma.fc.food_curt.domain.usecase.OrderUseCase;
import com.pragma.fc.food_curt.infraestructure.out.feign.adapter.NotificationFeignAdapter;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.INotificationClientFeign;
import com.pragma.fc.food_curt.infraestructure.out.jpa.adapter.OrderJpaAdapter;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IOrderOtpEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IOrderOtpRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IOrderRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IOrderStatusRepository;
import com.pragma.fc.food_curt.infraestructure.out.otp.adapter.SecureRandomOtpAdapter;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
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
    private final INotificationClientFeign notificationClientFeign;
    private final MessageSource messageSource;
    private final IOrderOtpRepository orderOtpRepository;
    private final IOrderOtpEntityMapper orderOtpEntityMapper;
    private final IUserClientPort userClientPort;

    @Value("${otp.expiration-minutes}")
    private Integer otpExpirationMinutes;

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(dishServicePort, orderPersistencePort(), restaurantServicePort, notificationClientPort(), otpServicePort(), otpExpirationMinutes, userClientPort);
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, dishServicePort, orderEntityMapper, dishEntityMapper, restaurantEntityMapper, orderStatusRepository, entityManager, orderOtpRepository, orderOtpEntityMapper);
    }

    @Bean
    public INotificationClientPort notificationClientPort() {
        return new NotificationFeignAdapter(notificationClientFeign, messageSource);
    }

    @Bean
    public IOtpServicePort otpServicePort() {
        return new SecureRandomOtpAdapter();
    }
}
