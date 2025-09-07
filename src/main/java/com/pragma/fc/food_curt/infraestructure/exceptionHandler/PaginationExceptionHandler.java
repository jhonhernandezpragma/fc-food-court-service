package com.pragma.fc.food_curt.infraestructure.exceptionHandler;

import com.pragma.fc.food_curt.domain.exception.InvalidPaginationParameterException;
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
public class PaginationExceptionHandler {

    @ExceptionHandler(InvalidPaginationParameterException.class)
    public ResponseEntity<ApiError> handleInvalidPaginationParameter(InvalidPaginationParameterException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
}
