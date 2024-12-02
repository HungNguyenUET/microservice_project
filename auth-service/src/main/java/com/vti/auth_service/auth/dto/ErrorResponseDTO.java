package com.vti.auth_service.auth.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class ErrorResponseDTO implements Serializable {
    private int status;

    @NonNull
    private String message;
}
