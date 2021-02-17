package com.wcreators.todo_api.services.user.created;

import com.wcreators.kafka_starter.dto.UserCreatedDTO;

public interface UserCreatedService {
    void consumeUserCreated(UserCreatedDTO userCreatedDTO);
}
