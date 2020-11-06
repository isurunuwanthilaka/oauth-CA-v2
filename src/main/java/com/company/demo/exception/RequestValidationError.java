package com.company.demo.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class RequestValidationError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<FieldError> fieldErrors;

    public RequestValidationError(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

}
