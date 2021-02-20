package com.wcreators.users_api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class AuthResponseDto {
    String token;
}
