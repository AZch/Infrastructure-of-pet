package com.wcreators.kafka_starter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreatedDTO {
    private Long id;
    private RoleDTO role;
}
