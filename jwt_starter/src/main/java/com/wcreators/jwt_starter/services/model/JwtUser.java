package com.wcreators.jwt_starter.services.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JwtUser {
    Long id;
    String role;
}
