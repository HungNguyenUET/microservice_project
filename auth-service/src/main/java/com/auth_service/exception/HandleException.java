package com.auth_service.exception;

import com.auth_service.dto.response.AuthenticationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HandleException extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String field = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            errors.put(field, message);
        }

        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleBadCredentialsException(UsernameNotFoundException ex) throws JsonProcessingException {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(authenticationResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) throws JsonProcessingException {
        log.info("Exception: {}", ex.toString());
        log.info("Exception message: {}", ex.getMessage());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toString());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) throws JsonProcessingException {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .message("Username or password invalid")
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(authenticationResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(ExpiredJwtException ex) throws JsonProcessingException {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(authenticationResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleMalformedJwtException(ValidationException ex) throws JsonProcessingException {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .message(ex.getMessage())
                .status(ex.getStatus().value())
                .build();

        return new ResponseEntity<>(authenticationResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleMalformedJwtException(Exception ex) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonObject.toString());
    }
}
