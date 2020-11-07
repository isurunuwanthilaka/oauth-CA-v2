package com.company.demo.exception;

public class EntitySaveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntitySaveException(String message) {
        super(message);
    }
}
