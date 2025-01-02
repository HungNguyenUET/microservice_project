package com.vti.auth_service.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotEmpty(message = "Username must not be empty")
    private String username;

    private String firstName;

    private String lastName;

    @NotNull(message = "Email must not be null")
    @Email(message = "Malformed email")
    private String email;

    @NotNull(message = "Password must not be null")
    private String password;

    @NotNull(message = "Role can not be null")
    @Pattern(regexp = "ADMIN|MANAGER|USER", message = "The role must be ADMIN, MANAGER or USER")
    private String role;
}
