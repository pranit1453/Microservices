package com.demo.user.exception;

import com.demo.user.generic.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle custom runtime exceptions
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Object>> handleBaseException(BaseException ex) {

        ApiResponse<Object> response = ApiResponse.builder()
                .status(ex.getStatus().value())
                .success(false)
                .message("Request Failed")
                .data(null)
                .failure_message(ex.getMessage())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }


    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {

        ApiResponse<Object> response = ApiResponse.builder()
                .status(400)
                .success(false)
                .message("Invalid Request")
                .data(null)
                .failure_message(ex.getMessage())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.badRequest().body(response);
    }









    // Fallback for any uncaught exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {

        ApiResponse<Object> response = ApiResponse.builder()
                .status(500)
                .success(false)
                .message("Internal Server Error")
                .data(null)
                .failure_message(ex.getMessage())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.internalServerError().body(response);
    }
}