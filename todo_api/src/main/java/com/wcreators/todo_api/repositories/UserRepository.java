package com.wcreators.todo_api.repositories;

import com.wcreators.todo_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndRoleName(Long id, String role);
}
