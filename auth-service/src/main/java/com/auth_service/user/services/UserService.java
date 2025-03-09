package com.auth_service.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.auth_service.model.User;
import com.auth_service.user.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
