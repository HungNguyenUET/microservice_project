package com.auth_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.auth_service.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
