package com.wcreators.users_api.services.user;

import com.wcreators.users_api.constants.Roles;
import com.wcreators.users_api.dto.RegistrationRequestDTO;
import com.wcreators.users_api.entities.Role;
import com.wcreators.users_api.entities.User;
import com.wcreators.users_api.repositories.RoleRepository;
import com.wcreators.users_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByIdAndRole(Long id, String role) {
        return userRepository.findByIdAndRole_Name(id, role);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        Optional<User> user = findByUserName(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())
                ? user
                : Optional.empty();
    }
}
