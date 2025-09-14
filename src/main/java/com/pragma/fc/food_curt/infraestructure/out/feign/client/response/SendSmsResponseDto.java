package com.pragma.fc.food_curt.infraestructure.out.feign.client.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSmsResponseDto {
    private String messageId;
    private String to;
}
