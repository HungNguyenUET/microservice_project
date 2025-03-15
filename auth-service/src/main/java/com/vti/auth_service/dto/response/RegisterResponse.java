package com.vti.auth_service.dto.response;

import lombok.Builder;

@Builder
public class RegisterResponse {
    private int status;
    private String message;
}
