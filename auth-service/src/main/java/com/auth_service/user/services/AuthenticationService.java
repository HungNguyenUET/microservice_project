package com.auth_service.user.services;

import com.auth_service.auth.dto.response.AuthenticationResponseDTO;
import com.auth_service.auth.dto.request.LoginRequestDTO;
import com.auth_service.auth.dto.request.RegisterRequestDTO;
import com.auth_service.auth.dto.response.RegisterResponseDTO;
import com.auth_service.exception.CustomException;
import com.auth_service.model.Role;
import com.auth_service.model.User;
import com.auth_service.user.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenTicationManager;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(Role.toEnum(registerRequestDTO.getRole()))
                .build();

        Optional<User> userFoundByEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> userFoundByUsername = userRepository.findByEmail(user.getUsername());
        if (userFoundByEmail.isPresent() || userFoundByUsername.isPresent()) {
            throw new ConstraintViolationException("User already exists!", null);
        }

        User userSaved = userRepository.save(user);

        return RegisterResponseDTO.builder()
                .status(HttpStatus.OK.value())
                .message("User created")
                .build();
    }

    public AuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO) {
        log.info("User request body login:");
        log.info("Login request dto: {}", loginRequestDTO);
        authenTicationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        Optional<User> userFoundByUsername = userRepository.findByUsername(loginRequestDTO.getUsername());
        if (userFoundByUsername.isPresent()) {
            User user = userFoundByUsername.get();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
            return AuthenticationResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userId(user.getId())
                    .message("Login successfully")
                    .status(HttpStatus.OK.value())
                    .build();
        } else {
            return AuthenticationResponseDTO.builder()
                    .message("Login failed")
                    .status(HttpStatus.FORBIDDEN.value())
                    .build();
        }
    }

    public AuthenticationResponseDTO refreshToken(HttpServletRequest req, HttpServletResponse res) throws CustomException {
        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return AuthenticationResponseDTO.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Unauthorized!")
                    .build();
        }

        String refreshToken = authHeader.substring(7);
        String userName = jwtService.extractUsername(refreshToken);
        if (userName == null || userName.isEmpty()) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Username is empty");
        }

        Optional<User> userFoundByUsername = userRepository.findByUsername(userName);
        if (userFoundByUsername.isEmpty()) {
            throw new UsernameNotFoundException(userName);
        }

        User user = userFoundByUsername.get();
        if (user.getRefreshToken() == null || user.getAccessToken().isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Token of the user revoked");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        user.setAccessToken(accessToken);
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .message("Refresh token successfully")
                .status(HttpStatus.OK.value())
                .build();
    }
}
