package com.company.demo.exception;

import com.company.demo.util.ResponseHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseHelper.setError(exception.getMessage());
    }

    @ExceptionHandler(EntitySaveException.class)
    public ResponseEntity handleEntitySaveException(EntitySaveException exception) {
        return ResponseHelper.setError(exception.getMessage());
    }
}
