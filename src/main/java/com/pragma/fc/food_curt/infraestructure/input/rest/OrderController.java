package com.pragma.fc.food_curt.infraestructure.input.rest;

import com.pragma.fc.food_curt.application.dto.request.CreateOrderRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateOrderResponseDto;
import com.pragma.fc.food_curt.application.handler.IOrderHandler;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiError;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.prefix}/orders")
public class OrderController {
    private final IOrderHandler orderHandler;

    public OrderController(IOrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Operation(
            summary = "Create order",
            description = "Requires CUSTOMER role",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created successfully",
                            content = @Content(contentSchema = @Schema(implementation = CreateOrderResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = """
                            1. Invalid input
                            2. Dishes should belong to same restaurant
                            3. There are duplicate dish IDs in the order
                            """,
                            content = @Content(contentSchema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "409", description = "User has an active order",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden: requires role ADMIN",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ApiSuccess<CreateOrderResponseDto>> createOrder(@RequestBody @Valid CreateOrderRequestDto dto) {
        CreateOrderResponseDto response = orderHandler.createOrder(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Order created successfully",
                        response
                ));
    }
}
