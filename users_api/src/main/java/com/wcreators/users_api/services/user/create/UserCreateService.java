package com.wcreators.users_api.services.user.create;

import com.wcreators.users_api.dto.RegistrationRequestDTO;
import com.wcreators.users_api.entities.User;
import com.wcreators.users_api.dto.UserCreatedModelDTO;

import java.util.Optional;

public interface UserCreateService {
    Optional<User> saveUser(RegistrationRequestDTO registrationDTO);

    void produceUserCreated(UserCreatedModelDTO userCreatedDTO);
}
