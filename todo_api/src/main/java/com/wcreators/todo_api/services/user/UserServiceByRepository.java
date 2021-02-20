package com.wcreators.todo_api.services.user;

import com.wcreators.todo_api.entities.User;
import com.wcreators.todo_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceByRepository implements UserService {

    private final UserRepository userRepository;

    public Optional<User> getByIdAndRole(Long id, String role) {
        return userRepository.findByIdAndRoleName(id, role);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
