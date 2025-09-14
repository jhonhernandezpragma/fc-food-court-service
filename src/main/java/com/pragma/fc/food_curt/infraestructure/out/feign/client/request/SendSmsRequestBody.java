package com.pragma.fc.food_curt.infraestructure.out.feign.client.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSmsRequestBody {
    private String phoneNumber;
    private String message;
}
