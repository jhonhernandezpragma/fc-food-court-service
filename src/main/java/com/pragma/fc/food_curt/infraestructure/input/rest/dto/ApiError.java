package com.pragma.fc.food_curt.infraestructure.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private String error;
    private Integer status;
    private String message;
    private String path;
}
