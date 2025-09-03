package com.pragma.fc.food_curt.infraestructure.configuration;

import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.spi.IRestaurantPersistencePort;
import com.pragma.fc.food_curt.domain.spi.IUserClientPort;
import com.pragma.fc.food_curt.domain.usecase.RestaurantUseCase;
import com.pragma.fc.food_curt.infraestructure.out.feign.adapter.UserFeignAdapter;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.IUserClientFeign;
import com.pragma.fc.food_curt.infraestructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestaurantBeanConfiguration {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IUserClientFeign userClientFeign;

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userClientPort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

   @Bean
    public IUserClientPort userClientPort() {
        return new UserFeignAdapter(userClientFeign);
    }

}
