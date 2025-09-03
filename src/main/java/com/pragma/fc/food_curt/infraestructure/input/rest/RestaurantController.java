package com.pragma.fc.food_curt.infraestructure.input.rest;

import com.pragma.fc.food_curt.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.fc.food_curt.application.dto.response.CreateRestaurantResponseDto;
import com.pragma.fc.food_curt.application.handler.IRestaurantHandler;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiError;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.prefix}/restaurants")
public class RestaurantController {
    private final IRestaurantHandler restaurantHandler;

    public RestaurantController(IRestaurantHandler restaurantHandler) {
        this.restaurantHandler = restaurantHandler;
    }

    @Operation(summary = "Create restaurant by owner",
        responses = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(contentSchema = @Schema(implementation = CreateRestaurantResponseDto.class))),
                @ApiResponse(responseCode = "404", description = """
                        1. Invalid restaurant name
                        2. Restaurant already exists
                        3. User role should be OWNER
                        """,
                        content = @Content(contentSchema = @Schema(implementation = ApiError.class))),
        }
    )
    @PostMapping
    public ResponseEntity<ApiSuccess<CreateRestaurantResponseDto>> createRestaurant(@RequestBody @Valid CreateRestaurantRequestDto dto) {
        CreateRestaurantResponseDto response = restaurantHandler.createRestaurant(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Restaurant created successfully",
                        response
                ));
    }
}
