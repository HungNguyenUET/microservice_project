package com.vti.auth_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vti.auth_service.model.User;
import com.vti.auth_service.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
