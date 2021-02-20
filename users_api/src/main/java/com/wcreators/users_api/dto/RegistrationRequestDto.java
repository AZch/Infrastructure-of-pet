package com.wcreators.users_api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@Builder
@AllArgsConstructor
public class RegistrationRequestDto {

    @NotEmpty
    @Size(min = 2)
    String username;

    @NotEmpty
    @Size(min = 2)
    String password;
}
