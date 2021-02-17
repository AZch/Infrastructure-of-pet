package com.wcreators.users_api.dto;

import com.wcreators.users_api.entities.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreatedModelDTO {
    private Long id;
    private Role role;
}
