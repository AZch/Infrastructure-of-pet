package com.wcreators.users_api.services.user.create.user_create;

import com.wcreators.users_api.constants.Errors;
import com.wcreators.users_api.constants.Roles;
import com.wcreators.users_api.entities.Role;
import com.wcreators.users_api.entities.User;
import com.wcreators.users_api.exceptions.BadRequestException;
import com.wcreators.users_api.repositories.RoleRepository;
import com.wcreators.users_api.repositories.UserRepository;
import com.wcreators.users_api.services.user.models.RegistrationUserModel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserCreateServiceByRepository implements UserCreateService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(RegistrationUserModel registrationUserModel) throws BadRequestException {
        val role = roleRepository
                .findByName(Roles.USER.getName())
                .orElseGet(() -> roleRepository.save(Role.builder().name(Roles.USER.getName()).build()));
        userRepository
                .findByUsername(registrationUserModel.getUsername())
                .ifPresent(u -> { throw new BadRequestException(Errors.AuthError.USERNAME_ALREADY_IN_USE); });
        val user = User.builder()
            .username(registrationUserModel.getUsername())
            .password(passwordEncoder.encode(registrationUserModel.getPassword()))
            .role(role)
            .build();
        return userRepository.save(user);
    }
}
