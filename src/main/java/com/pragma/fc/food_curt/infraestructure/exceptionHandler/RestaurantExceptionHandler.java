package com.pragma.fc.food_curt.infraestructure.exceptionHandler;

import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidNameException;
import com.pragma.fc.food_curt.domain.exception.RestaurantInvalidUserRoleException;
import com.pragma.fc.food_curt.infraestructure.exception.OwnerRestaurantNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.RestaurantAlreadyExistsException;
import com.pragma.fc.food_curt.infraestructure.exception.RestaurantNotFoundException;
import com.pragma.fc.food_curt.infraestructure.exception.WorkerRestaurantNotFoundException;
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
public class RestaurantExceptionHandler {

    @ExceptionHandler(RestaurantInvalidNameException.class)
    public ResponseEntity<ApiError> handleRestaurantInvalidName(RestaurantInvalidNameException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(RestaurantInvalidUserRoleException.class)
    public ResponseEntity<ApiError> handleRestaurantInvalidUserRole(RestaurantInvalidUserRoleException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleRestaurantAlreadyExists(RestaurantAlreadyExistsException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ApiError> handleRestaurantNotFound(RestaurantNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(OwnerRestaurantNotFoundException.class)
    public ResponseEntity<ApiError> handleOwnerRestaurantNotFound(OwnerRestaurantNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(WorkerRestaurantNotFoundException.class)
    public ResponseEntity<ApiError> handleWorkerRestaurantNotFound(WorkerRestaurantNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }
}
