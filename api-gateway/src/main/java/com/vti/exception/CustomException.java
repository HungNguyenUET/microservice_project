package com.vti.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
