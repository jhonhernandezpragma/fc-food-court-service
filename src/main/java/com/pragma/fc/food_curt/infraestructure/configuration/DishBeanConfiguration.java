package com.pragma.fc.food_curt.infraestructure.configuration;

import com.pragma.fc.food_curt.domain.api.IDishServicePort;
import com.pragma.fc.food_curt.domain.api.IRestaurantServicePort;
import com.pragma.fc.food_curt.domain.spi.IDishPersistencePort;
import com.pragma.fc.food_curt.domain.usecase.DishUseCase;
import com.pragma.fc.food_curt.infraestructure.out.jpa.adapter.DishJpaAdapter;
import com.pragma.fc.food_curt.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IDishCategoryRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IDishRepository;
import com.pragma.fc.food_curt.infraestructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DishBeanConfiguration {
    public final IDishRepository dishRepository;
    public final IDishCategoryRepository dishCategoryRepository;
    public final IRestaurantRepository restaurantRepository;
    public final IRestaurantServicePort restaurantServicePort;
    public final IDishEntityMapper dishEntityMapper;

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), restaurantServicePort);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, restaurantRepository, dishCategoryRepository, dishEntityMapper);
    }

}
