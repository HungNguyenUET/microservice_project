package com.vti.auth_service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDTO {
    private String username;
    private String password;
}
