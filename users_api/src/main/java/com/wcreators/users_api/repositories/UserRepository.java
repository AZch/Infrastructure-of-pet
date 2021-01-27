package com.wcreators.users_api.repositories;

import com.wcreators.users_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndRole_Name(Long id, String role);
}
