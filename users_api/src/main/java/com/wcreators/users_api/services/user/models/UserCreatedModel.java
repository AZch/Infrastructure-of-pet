package com.wcreators.users_api.services.user.models;

import com.wcreators.users_api.entities.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class UserCreatedModel {
    Long id;
    Role role;
}
