package com.pragma.fc.food_curt.infraestructure.exceptionHandler;

import com.pragma.fc.food_curt.domain.exception.ActiveOrderAlreadyExistsException;
import com.pragma.fc.food_curt.domain.exception.DishesFromDifferentRestaurantsException;
import com.pragma.fc.food_curt.infraestructure.exception.OrderStatusNotFoundException;
import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderExceptionHandler {

    @ExceptionHandler(ActiveOrderAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleActiveOrderAlreadyExists(ActiveOrderAlreadyExistsException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(DishesFromDifferentRestaurantsException.class)
    public ResponseEntity<ApiError> handleDishesFromDifferentRestaurants(DishesFromDifferentRestaurantsException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(OrderStatusNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderStatusNotFound(OrderStatusNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }
}
