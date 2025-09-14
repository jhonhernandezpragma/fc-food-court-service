package com.pragma.fc.food_curt.infraestructure.out.feign.client.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDto {
    private Long documentNumber;
    private String name;
    private String lastname;
    private String email;
    private LocalDate birthDate;
    private String phone;
    private UserRoleResponseDto role;
}
