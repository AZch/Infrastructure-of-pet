package com.wcreators.users_api.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
public class AuthRequestDto {
    @NotEmpty
    @Size(min = 2)
    String username;

    @NotEmpty
    @Size(min = 2)
    String password;
}
