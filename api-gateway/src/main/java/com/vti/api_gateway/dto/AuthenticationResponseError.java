package com.vti.api_gateway.dto;

import lombok.Getter;

@Getter
public class AuthenticationResponseError {
    private int status;
    private String message;

    public AuthenticationResponseError(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
