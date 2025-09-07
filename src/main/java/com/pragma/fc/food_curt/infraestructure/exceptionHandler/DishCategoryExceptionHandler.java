package com.pragma.fc.food_curt.infraestructure.exceptionHandler;

import com.pragma.fc.food_curt.domain.exception.OwnerNotAuthorizedForRestaurantException;
import com.pragma.fc.food_curt.infraestructure.exception.DishCategoryNotFoundException;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DishCategoryExceptionHandler {

    @ExceptionHandler(DishCategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleDishCategoryNonPositivePrice(DishCategoryNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(OwnerNotAuthorizedForRestaurantException.class)
    public ResponseEntity<ApiError> handleOwnerNotAuthorizedForRestaurant(OwnerNotAuthorizedForRestaurantException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }
}
