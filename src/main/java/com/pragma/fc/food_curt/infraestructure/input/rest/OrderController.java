package com.pragma.fc.food_curt.infraestructure.input.rest;

import com.pragma.fc.food_curt.application.dto.request.CreateOrderRequestDto;
import com.pragma.fc.food_curt.application.dto.response.OrderResponseDto;
import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;
import com.pragma.fc.food_curt.application.handler.IOrderHandler;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiError;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/orders")
@Validated
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
                            content = @Content(contentSchema = @Schema(implementation = OrderResponseDto.class))),
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
    public ResponseEntity<ApiSuccess<OrderResponseDto>> createOrder(@RequestBody @Valid CreateOrderRequestDto dto) {
        OrderResponseDto response = orderHandler.createOrder(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Order created successfully",
                        response
                ));
    }

    @Operation(
            summary = "Get all orders sorted by name",
            description = "Returns a paginated list of orders sorted by name. Requires authentication.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Paginated list of dishes retrieved successfully",
                            content = @Content(schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = """
                                    1. Parameter 'page' < 1
                                    2. Parameter 'size' > 100
                                    """,
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized: missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden: requires role WORKER",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
            }
    )
    @PreAuthorize("hasRole('WORKER')")
    @GetMapping
    public ResponseEntity<ApiSuccess<PaginationResponseDto<OrderResponseDto>>> getAllByStatusAndPaginated(
            @RequestParam @NotNull Integer page,
            @RequestParam @NotNull Integer size,
            @RequestParam(required = false) Integer orderStatusId
    ) {
        PaginationResponseDto<OrderResponseDto> response = orderHandler.getPaginatedByStatusSortedByDate(page, size, Optional.ofNullable(orderStatusId));
        return ResponseEntity
                .ok(new ApiSuccess<>(
                        "Paginated list of orders retrieved successfully",
                        response
                ));
    }

    @Operation(
            summary = "Assign a worker to an order",
            description = "Assigns the authenticated worker to the specified order. Requires authentication and role WORKER.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Worker assigned to order successfully",
                            content = @Content(schema = @Schema(implementation = OrderResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Invalid order status for assignment",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Order already assigned to another worker",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = """
                                        1. Forbidden: requires role WORKER,
                                        2. Worker and restaurant do not match
                                    """,
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized: missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    )
            }
    )
    @PreAuthorize("hasRole('WORKER')")
    @PutMapping("/{orderId}/assign-worker")
    public ResponseEntity<ApiSuccess<OrderResponseDto>> assignWorkerToOrder(@PathVariable @NotNull Integer orderId) {
        OrderResponseDto response = orderHandler.assignWorkerToOrder(orderId);
        return ResponseEntity
                .ok(new ApiSuccess<>(
                        "Worker assigned to order successfully",
                        response
                ));
    }
}
