<<<<<<<< HEAD:auth-service/src/main/java/com/vti/auth_service/services/UserService.java
package com.vti.auth_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vti.auth_service.model.User;
import com.vti.auth_service.repo.UserRepository;
========
package com.auth_service.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.auth_service.model.User;
import com.auth_service.user.repo.UserRepository;
>>>>>>>> 47f37699d2ea9828b383ce775658bc07a27d7ddb:auth-service/src/main/java/com/auth_service/user/services/UserService.java

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
