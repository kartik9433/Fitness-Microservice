package com.fitness.userservice.repository;

import com.fitness.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByEmail(String email);

    Boolean existsByKeycloakId(String userId);

    boolean existsByEmail(String email);

    boolean exitsByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid Email format") String email);
}
