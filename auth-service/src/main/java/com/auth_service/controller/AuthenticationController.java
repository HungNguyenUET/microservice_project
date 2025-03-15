package com.vti.auth_service.controller;

import com.vti.auth_service.dto.response.AuthenticationResponse;
import com.vti.auth_service.dto.request.LoginRequest;
import com.vti.auth_service.dto.request.RegisterRequest;
import com.vti.auth_service.dto.response.RegisterResponse;
import com.vti.auth_service.exception.ValidationException;
import com.vti.auth_service.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {
    public final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        RegisterResponse registerResponse = authenticationService.register(registerRequest);
        return ResponseEntity
                .status(registerResponse.getStatus())
                .body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        log.info("Password: {}", loginRequest.getPassword());
        log.info("Username: {}", loginRequest.getUsername());
        AuthenticationResponse authenticationResponse = authenticationService.login(loginRequest);
        return ResponseEntity
                .status(authenticationResponse.getStatus())
                .body(authenticationResponse);
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshToken(@RequestHeader("Authorization") String authHeader) throws ValidationException {
        log.info("Request: {}", req);
        return authenticationService.refreshToken(authHeader);
    }
}
