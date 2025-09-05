package com.pragma.fc.food_curt.infraestructure.configuration;

import com.pragma.fc.food_curt.domain.spi.ITokenServicePort;
import com.pragma.fc.food_curt.infraestructure.out.security.adapter.JwtTokenServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AuthBeanConfiguration {

    @Bean
    public ITokenServicePort tokenServicePort() {
        return new JwtTokenServiceAdapter();
    }
}
