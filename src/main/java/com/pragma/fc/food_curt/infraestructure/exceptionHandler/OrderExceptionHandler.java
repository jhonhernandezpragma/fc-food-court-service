package com.pragma.fc.food_curt.infraestructure.exceptionHandler;

import com.pragma.fc.food_curt.domain.exception.ActiveOrderAlreadyExistsException;
import com.pragma.fc.food_curt.domain.exception.DishesFromDifferentRestaurantsException;
import com.pragma.fc.food_curt.domain.exception.InvalidOrderStatusForAssignmentException;
import com.pragma.fc.food_curt.domain.exception.OrderAlreadyAssignedException;
import com.pragma.fc.food_curt.domain.exception.OrderNotInPreparationException;
import com.pragma.fc.food_curt.domain.exception.OrderWorkerMismatchException;
import com.pragma.fc.food_curt.domain.exception.WorkerRestaurantMismatchException;
import com.pragma.fc.food_curt.infraestructure.exception.OrderNotFoundException;
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

    @ExceptionHandler(InvalidOrderStatusForAssignmentException.class)
    public ResponseEntity<ApiError> handleInvalidOrderStatus(InvalidOrderStatusForAssignmentException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(OrderAlreadyAssignedException.class)
    public ResponseEntity<ApiError> handleOrderAlreadyAssigned(OrderAlreadyAssignedException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFound(OrderNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(WorkerRestaurantMismatchException.class)
    public ResponseEntity<ApiError> handleWorkerRestaurantMismatch(WorkerRestaurantMismatchException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    @ExceptionHandler(OrderWorkerMismatchException.class)
    public ResponseEntity<ApiError> handleOrderWorkerMismatch(OrderWorkerMismatchException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }


    @ExceptionHandler(OrderNotInPreparationException.class)
    public ResponseEntity<ApiError> handleOrderNotInPreparation(OrderNotInPreparationException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

}
