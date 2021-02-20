package com.wcreators.todo_api.services.user;

import com.wcreators.todo_api.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByIdAndRole(Long id, String role);

    Optional<User> getById(Long id);

    User save(User user);
}
