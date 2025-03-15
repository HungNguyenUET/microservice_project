<<<<<<<< HEAD:auth-service/src/main/java/com/vti/auth_service/services/AuthenticationService.java
package com.vti.auth_service.services;

import com.vti.auth_service.dto.response.AuthenticationResponse;
import com.vti.auth_service.dto.request.LoginRequest;
import com.vti.auth_service.dto.request.RegisterRequest;
import com.vti.auth_service.dto.response.RegisterResponse;
import com.vti.auth_service.exception.ValidationException;
import com.vti.auth_service.model.Role;
import com.vti.auth_service.model.User;
import com.vti.auth_service.repo.UserRepository;
========
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
>>>>>>>> 47f37699d2ea9828b383ce775658bc07a27d7ddb:auth-service/src/main/java/com/auth_service/user/services/AuthenticationService.java
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final int TOKEN_INDEX = 7;

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenTicationManager;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest registerRequest) {
        //Check email, username is existed in db
        String email = registerRequest.getEmail();
        String userName = registerRequest.getUsername();

        Optional<User> userFoundByEmail = userRepository.findByEmail(email);
        Optional<User> userFoundByUsername = userRepository.findByUsername(userName);

        if (userFoundByEmail.isPresent() || userFoundByUsername.isPresent()) {
            throw new ConstraintViolationException("User already exists!", null);
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.toEnum(registerRequest.getRole()))
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
                .status(HttpStatus.OK.value())
                .message("User created")
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        //Authenticate username and password
        log.info("User request body login:");
        log.info("Login request dto: {}", loginRequest);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        authenTicationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        //Generate access token and refresh token
        Optional<User> userFoundByUsername = userRepository.findByUsername(loginRequest.getUsername());
        if (userFoundByUsername.isPresent()) {
            User user = userFoundByUsername.get();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            //Save access token and refresh token into user in database
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            //Response access token and refresh token to client
            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userId(user.getId())
                    .message("Login successfully")
                    .status(HttpStatus.OK.value())
                    .build();
        } else {
            return AuthenticationResponse.builder()
                    .message("Login failed")
                    .status(HttpStatus.FORBIDDEN.value())
                    .build();
        }
    }

    public AuthenticationResponse refreshToken(String authHeader) throws ValidationException {
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return AuthenticationResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Unauthorized!")
                    .build();
        }

        String refreshToken = authHeader.substring(TOKEN_INDEX);

        //Get userName from refreshToken
        String userName = jwtService.extractUsername(refreshToken);
        if (!StringUtils.hasText(userName)) {
            throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR, "Username is empty");
        }

    //Get User's data from database
        Optional<User> userFoundByUsername = userRepository.findByUsername(userName);
        if (userFoundByUsername.isEmpty()) {
            throw new UsernameNotFoundException(userName);
        }

        User user = userFoundByUsername.get();
        if (!StringUtils.hasText(user.getAccessToken()) || !StringUtils.hasText(user.getRefreshToken())) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, "Token of the user revoked");
        }

        //Generate access token and refresh token
        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        user.setAccessToken(accessToken);
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        //Response access token and refresh token to client
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .message("Refresh token successfully")
                .status(HttpStatus.OK.value())
                .build();
    }
}
