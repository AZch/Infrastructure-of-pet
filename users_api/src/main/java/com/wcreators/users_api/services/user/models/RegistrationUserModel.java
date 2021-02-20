package com.wcreators.users_api.services.user.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegistrationUserModel {
    String username;
    String password;
}
