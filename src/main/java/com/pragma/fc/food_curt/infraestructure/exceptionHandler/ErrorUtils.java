package com.pragma.fc.food_curt.infraestructure.exceptionHandler;

import com.pragma.fc.food_curt.infraestructure.input.rest.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public class ErrorUtils {
    private ErrorUtils() {
    }

    public static ResponseEntity<ApiError> buildError(HttpStatus status, String message, WebRequest request) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(status).body(error);
    }
}
