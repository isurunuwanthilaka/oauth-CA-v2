package com.company.demo.util;

import com.company.demo.exception.ApiError;
import com.company.demo.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/*
 * This layer is applicable for controller adviser and controllers
 */
@Component
@Slf4j
public class ResponseHelper {

    public static ResponseEntity setResponse(Object response) {
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity setResponse(Object response, String logMessage,
                                             Object... objects) {
        log.info(logMessage, objects);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity setResponse(HttpStatus status, Object response, String logMessage,
                                             Object... objects) {
        log.info(logMessage, objects);
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity setResponse(HttpStatus status, Object response) {
        return ResponseEntity.status(status).body(response);
    }


    public static ResponseEntity setError(HttpStatus status, ErrorMessage error, String logMessage,
                                          Object... objects) {
        log.error(logMessage, objects);
        ApiError apiError = new ApiError(status, error.getMessage(), MessageFormat.format(logMessage, objects));
        return ResponseEntity.status(status).body(apiError);
    }

    public static ResponseEntity setError(ErrorMessage error, String logMessage, Object... objects) {
        log.error(logMessage, objects);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error.getMessage(), MessageFormat.format(logMessage, objects));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    public static ResponseEntity setError(String error, String logMessage, Object... objects) {
        log.error(logMessage, objects);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, MessageFormat.format(logMessage, objects));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
