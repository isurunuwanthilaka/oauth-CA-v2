package com.company.demo.util;

import com.company.demo.exception.ApiError;
import com.company.demo.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHelper {

    public static ResponseEntity setResponse(Object response) {
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity setResponse(HttpStatus status, Object response) {
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity setError(HttpStatus status, ErrorMessage error) {
        ApiError apiError = new ApiError(status, error.getError());
        return ResponseEntity.status(status).body(apiError);
    }

    public static ResponseEntity setError(ErrorMessage error) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error.getError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    public static ResponseEntity setError(String error) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
