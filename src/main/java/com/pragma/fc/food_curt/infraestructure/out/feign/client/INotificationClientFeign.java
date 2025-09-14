package com.pragma.fc.food_curt.infraestructure.out.feign.client;

import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiSuccess;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.request.SendSmsRequestBody;
import com.pragma.fc.food_curt.infraestructure.out.feign.client.response.SendSmsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "notification-service", url = "${notification.service.url}")
public interface INotificationClientFeign {
    @PostMapping("/sms")
    ApiSuccess<SendSmsResponseDto> sendSms(
            @RequestBody SendSmsRequestBody body,
            @RequestHeader("Authorization") String bearerToken
    );
}
