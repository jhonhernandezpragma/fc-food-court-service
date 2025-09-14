package com.pragma.fc.food_curt.infraestructure.out.feign.client;

import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiSuccess;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${users.service.url}")
public interface IUserClientFeign {
    @GetMapping("/{documentNumber}/user")
    ApiSuccess<UserResponseDto> getUserByDocumentNumber(
            @PathVariable Long documentNumber,
            @RequestHeader("Authorization") String bearerToken
    );
}