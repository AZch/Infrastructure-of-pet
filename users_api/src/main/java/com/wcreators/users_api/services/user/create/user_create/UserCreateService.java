package com.wcreators.users_api.services.user.create.user_create;

import com.wcreators.users_api.entities.User;
import com.wcreators.users_api.services.user.models.RegistrationUserModel;

import java.util.Optional;

public interface UserCreateService {
    User saveUser(RegistrationUserModel registrationUserModel);
}
