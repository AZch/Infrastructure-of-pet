package com.wcreators.users_api.controllers.mappers;

import com.wcreators.jwt_starter.services.model.JwtUser;
import com.wcreators.users_api.dto.RegistrationRequestDto;
import com.wcreators.users_api.entities.User;
import com.wcreators.users_api.services.user.models.RegistrationUserModel;
import com.wcreators.users_api.services.user.models.UserCreatedModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public RegistrationUserModel dtoToRegistrationModel(RegistrationRequestDto registrationRequestDTO) {
        return RegistrationUserModel.builder()
                .username(registrationRequestDTO.getUsername())
                .password(registrationRequestDTO.getPassword())
                .build();
    }

    public UserCreatedModel userToCreatedModel(User user) {
        return UserCreatedModel.builder()
                .id(user.getId())
                .role(user.getRole())
                .build();
    }

    public JwtUser userToJwtUser(User user) {
        return JwtUser.builder()
                .id(user.getId())
                .role(user.getRole().getName())
                .build();
    }
}
