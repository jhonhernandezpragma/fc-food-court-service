package com.pragma.fc.food_curt.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDto {
    private Long nit;
    private Long ownerDocumentNumber;
    private String name;
    private String address;
    private String phone;
    private String logoUrl;
}
