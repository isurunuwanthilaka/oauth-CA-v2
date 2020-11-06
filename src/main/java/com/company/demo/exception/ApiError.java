package com.company.demo.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {

    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    private String message;
    @JsonIgnore
    private String debugMessage;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public ApiError(HttpStatus httpStatus, String debugMessage) {
        this();
        this.httpStatus = httpStatus;
        this.message = "Something went wrong. Please contact <company> support.";
        this.debugMessage = debugMessage;
    }

    public ApiError(HttpStatus httpStatus, String message, String debugMessage) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = debugMessage;
    }

}