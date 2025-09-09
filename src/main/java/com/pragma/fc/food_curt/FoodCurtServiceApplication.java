package com.pragma.fc.food_curt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class FoodCurtServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodCurtServiceApplication.class, args);
    }
}
