package com.wcreators.todo_api.services;

import com.wcreators.todo_api.entities.User;
import com.wcreators.todo_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByIdAndRole(Long id, String role) {
        return userRepository.findByIdAndRoleName(id, role);
    }
}
