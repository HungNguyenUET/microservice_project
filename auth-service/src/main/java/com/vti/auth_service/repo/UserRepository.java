<<<<<<<< HEAD:auth-service/src/main/java/com/vti/auth_service/repo/UserRepository.java
package com.vti.auth_service.repo;
========
package com.auth_service.user.repo;
>>>>>>>> 47f37699d2ea9828b383ce775658bc07a27d7ddb:auth-service/src/main/java/com/auth_service/user/repo/UserRepository.java

import org.springframework.data.jpa.repository.JpaRepository;
import com.auth_service.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
