package com.company.demo.exception;

import lombok.Getter;

public enum ErrorMessage {

    MALFORMED_REQUEST("Malformed request.Check the Authorization header."),
    INVALID_JWT("Invalid JWT."),
    UNAUTHENTICATED("User not authenticated.");

    @Getter
    private String error;

    ErrorMessage(String err) {
        this.error = err;
    }
}
