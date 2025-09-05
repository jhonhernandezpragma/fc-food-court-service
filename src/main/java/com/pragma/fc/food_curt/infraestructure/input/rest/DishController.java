package com.pragma.fc.food_curt.infraestructure.input.rest;

import com.pragma.fc.food_curt.application.dto.request.CreateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.request.UpdateDishRequestDto;
import com.pragma.fc.food_curt.application.dto.response.DishResponseDto;
import com.pragma.fc.food_curt.application.handler.IDishHandler;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.prefix}/dishes")
public class DishController {
    private final IDishHandler dishHandler;

    public DishController(IDishHandler dishHandler) {
        this.dishHandler = dishHandler;
    }

    @Operation(
            summary = "Create dish",
            description = "Requires role OWNER",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish created",
                            content = @Content(contentSchema = @Schema(implementation = DishResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = """
                        1. Dish price must be greater than zero
                        2. Invalid request payload (validation error)
                        """, content = @Content(contentSchema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "404", description = """
                        1. Dish category not found
                        2. Restaurant not found
                        """, content = @Content(contentSchema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "User already exists",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "403", description = "Missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
            }
    )
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<ApiSuccess<DishResponseDto>> createDish(@RequestBody @Valid CreateDishRequestDto dto) {
        DishResponseDto response = dishHandler.createDish(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess<>(
                        "Dish created successfully",
                        response
                ));
    }

    @Operation(
            summary = "Update dish",
            description = "Requires role OWNER",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dish updated",
                            content = @Content(contentSchema = @Schema(implementation = DishResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = """
                        1. Dish price must be greater than zero
                        2. Invalid request payload (validation error)
                        """, content = @Content(contentSchema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "404", description = "Dish not found",
                            content = @Content(contentSchema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "User already exists",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "403", description = "Missing or invalid access token",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
            }
    )
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiSuccess<DishResponseDto>> updateDish(@RequestBody @Valid UpdateDishRequestDto dto, @PathVariable Integer id) {
        DishResponseDto response = dishHandler.updateDish(id, dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess<>(
                        "Dish updated successfully",
                        response
                ));
    }
}
