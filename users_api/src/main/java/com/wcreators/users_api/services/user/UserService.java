package com.wcreators.users_api.services.user;

import com.wcreators.users_api.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByIdAndRole(Long id, String role);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
