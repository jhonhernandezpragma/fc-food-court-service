package com.pragma.fc.food_curt.infraestructure.input.rest;

import com.pragma.fc.food_curt.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.fc.food_curt.application.dto.response.PaginationResponseDto;
import com.pragma.fc.food_curt.application.dto.response.RestaurantListItemResponseDto;
import com.pragma.fc.food_curt.application.dto.response.RestaurantResponseDto;
import com.pragma.fc.food_curt.application.dto.response.WorkerRestaurantResponseDto;
import com.pragma.fc.food_curt.application.handler.IRestaurantHandler;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.prefix}/restaurants")
@Validated
public class RestaurantController {
    private final IRestaurantHandler restaurantHandler;

    public RestaurantController(IRestaurantHandler restaurantHandler) {
        this.restaurantHandler = restaurantHandler;
    }

    @Operation(
            summary = "Create restaurant",
            description = "Requires role ADMIN",
            responses = {
            @ApiResponse(responseCode = "201", description = "Restaurant created",
                    content = @Content(contentSchema = @Schema(implementation = RestaurantResponseDto.class))),
                @ApiResponse(responseCode = "404", description = """
                        1. Invalid restaurant name
                        2. Restaurant already exists
                        3. User role should be OWNER
                        """,
                        content = @Content(contentSchema = @Schema(implementation = ApiError.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized: missing or invalid access token",
                        content = @Content(schema = @Schema(implementation = ApiError.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden: requires role ADMIN",
                        content = @Content(schema = @Schema(implementation = ApiError.class)))
        }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiSuccess<RestaurantResponseDto>> createRestaurant(@RequestBody @Valid CreateRestaurantRequestDto dto) {
        RestaurantResponseDto response = restaurantHandler.createRestaurant(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Restaurant created successfully",
                        response
                ));
    }

    @Operation(
            summary = "Get restaurant NIT by owner",
            description = "Requires role OWNER",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Restaurant NIT retrieved successfully",
                            content = @Content(schema = @Schema(implementation = Long.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No restaurant found for the given owner",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized: missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden: requires role OWNER",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/me")
    public ResponseEntity<ApiSuccess<Long>> getRestaurantNitByOwner() {
        Long response = restaurantHandler.getRestaurantNitByOwner();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Restaurant retrieved successfully",
                        response
                ));
    }

    @Operation(
            summary = "Assign a worker to a restaurant",
            description = "Requires role OWNER",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Worker assigned to restaurant successfully",
                            content = @Content(schema = @Schema(implementation = WorkerRestaurantResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurant or owner not found",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Worker is already assigned to this restaurant",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized: missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden: requires role OWNER",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    )
            }
    )
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{restaurantNit}/workers/{userDocumentNumber}")
    public ResponseEntity<ApiSuccess<WorkerRestaurantResponseDto>> assignWorkerToRestaurant(@PathVariable Long restaurantNit, @PathVariable Long userDocumentNumber) {
        WorkerRestaurantResponseDto response = restaurantHandler.assignWorkerToRestaurant(restaurantNit, userDocumentNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Worker assigned to restaurant successfully",
                        response
                ));
    }

    @Operation(
            summary = "Get all restaurants sorted by name",
            description = "Returns a paginated list of restaurants sorted by name. Requires authentication.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Paginated list of restaurants retrieved successfully",
                            content = @Content(schema = @Schema(implementation = RestaurantListItemResponseDto.class))),
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
            }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApiSuccess<PaginationResponseDto<RestaurantListItemResponseDto>>> getAllPaginatedAndSortedByName(
            @RequestParam @NotNull Integer page,
            @RequestParam @NotNull Integer size
    ) {
        PaginationResponseDto<RestaurantListItemResponseDto> response = restaurantHandler.getAllPaginatedAndSortedByName(page, size);
        return ResponseEntity
                .ok(new ApiSuccess<>(
                        "Paginated list of restaurants retrieved successfully",
                        response
                ));
    }
}
